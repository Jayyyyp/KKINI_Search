package com.kkini.search.service;

import com.kkini.search.entity.Ratings;
import com.kkini.search.entity.Users;
import com.kkini.search.repository.ItemRepository;
import com.kkini.search.repository.RatingRepository;
import com.kkini.search.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class RatingService {

    private final RatingRepository ratingRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    @Value("${app.image-storage-path}")
    private String imageStoragePath;

    public List<Ratings> getRatingForItemOrderedByDate(Long itemId) {
        return ratingRepository.findByItemIdOrderByUpdatedAtDesc(itemId);
    }

    public List<Ratings> getRatingsForItem(Long itemId){
        return ratingRepository.findByItemId(itemId);
    }

    public Ratings saveRating(Long itemId, Long userId, int ratingValue, String rateText, String rateImage) {
        Users users = userRepository.findById(userId).orElse(null);
        Ratings rating = new Ratings();
        rating.setItemId(itemId);
        rating.setUsers(users);
        rating.setRatingValue(ratingValue);
        rating.setRatingText(rateText);
        rating.setRatingImage(rateImage);  // 이미지 설정

        Ratings savedRating = ratingRepository.save(rating);

        updateAverageRating(itemId);

        return savedRating;
    }

    public String saveImage(MultipartFile imageFile) throws IOException {
        String originalFilename = imageFile.getOriginalFilename();
        String savePath = imageStoragePath + originalFilename;

        File fileToSave = new File(savePath);
        imageFile.transferTo(fileToSave); // 이미지 파일을 지정한 위치에 저장

        return "/images/" + originalFilename; // 저장된 이미지의 URL을 반환
    }


    public Ratings findById(Long ratingId, Long userId) {
        return ratingRepository.findById(ratingId)
                .orElseThrow(() -> new IllegalArgumentException("Rating not found"));
    }

    public void delete(Ratings rating) {
        ratingRepository.delete(rating);
    }
    public void updateAverageRating(Long itemId) {
        List<Ratings> ratings = ratingRepository.findByItemId(itemId);

        double average = ratings.stream()
                .mapToInt(Ratings::getRatingValue)
                .average()
                .orElse(0.0);

        itemRepository.saveUpdatedRating(itemId, average);
    }

    public List<Ratings> getRatingsForUser(Long userId) {
        return ratingRepository.findUser(userId);
    }

    public Ratings findRatingById(Long ratingId) {
        return ratingRepository.findById(ratingId).orElse(null);
    }

    public void updateRating(Long ratingId, Long userId, int ratingValue, String ratingText) {
        Ratings rating = ratingRepository.findById(ratingId).orElse(null);

        if (rating == null || !rating.getUsers().getUserId().equals(userId)) {
            throw new IllegalArgumentException("Invalid rating or user");
        }

        rating.setRatingValue(ratingValue);
        rating.setRatingText(ratingText);
        ratingRepository.save(rating);

        Long itemId = rating.getItemId();
        updateAverageRating(itemId);
    }
}