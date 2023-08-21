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
import java.util.NoSuchElementException;
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

    public List<Ratings> getRatingsForItem(Long itemId) {
        return ratingRepository.findByItemIdOrderByUpdatedAtDesc(itemId);
    }

    public Ratings saveRating(Long itemId, Long userId, int ratingValue, String rateText, String[] rateImages) {
        if (userId == null) throw new IllegalArgumentException("UserId cannot be null");
        if (rateImages == null) throw new IllegalArgumentException("RateImages cannot be null");

        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("No user found with ID: " + userId));

        Ratings rating = new Ratings();
        rating.setItemId(itemId);
        rating.setUsers(user);
        rating.setRatingValue(ratingValue);
        rating.setRatingText(rateText);

        // Set images to the rating based on the number of images provided.
        if (rateImages.length > 0 && rateImages[0] != null && !rateImages[0].isEmpty()) {
            rating.setRatingImage1(rateImages[0]);
        }
        if (rateImages.length > 1 && rateImages[1] != null && !rateImages[1].isEmpty()) {
            rating.setRatingImage2(rateImages[1]);
        }
        if (rateImages.length > 2 && rateImages[2] != null && !rateImages[2].isEmpty()) {
            rating.setRatingImage3(rateImages[2]);
        }
        if (rateImages.length > 3 && rateImages[3] != null && !rateImages[3].isEmpty()) {
            rating.setRatingImage4(rateImages[3]);
        }

        Ratings savedRating = ratingRepository.save(rating);
        updateAverageRating(itemId);
        return savedRating;
    }

    public String saveImage(MultipartFile imageFile) throws IOException {
        String originalFilename = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
        String savePath = imageStoragePath + File.separator + originalFilename;

        File fileToSave = new File(savePath);
        imageFile.transferTo(fileToSave);

        return "/images/" + originalFilename;
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

    public void deleteImage(String imagePath) {
        // DB에서 이미지 경로 삭제
        ratingRepository.deleteRatingImage1ByPath(imagePath);
        ratingRepository.deleteRatingImage2ByPath(imagePath);
        ratingRepository.deleteRatingImage3ByPath(imagePath);
        ratingRepository.deleteRatingImage4ByPath(imagePath);

        // 물리적으로 storage 공간의 이미지 삭제
        try {
            Path path = Paths.get(imagePath);
            Files.deleteIfExists(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateRatingWithImage(Long ratingId, Long userId, int ratingValue, String ratingText, String[] rateImages) {
        Ratings rating = getRatingForUser(ratingId, userId);

        rating.setRatingValue(ratingValue);
        rating.setRatingText(ratingText);
        setRatingImages(rateImages, rating);

        ratingRepository.save(rating);
        updateAverageRating(rating.getItemId());
    }

    private Ratings getRatingForUser(Long ratingId, Long userId) {
        Ratings rating = ratingRepository.findById(ratingId).orElse(null);

        if (rating == null || !rating.getUsers().getUserId().equals(userId)) {
            throw new IllegalArgumentException("Invalid rating or user");
        }

        return rating;
    }

    private void setRatingImages(String[] rateImages, Ratings rating) {
        if (rateImages.length > 0 && rateImages[0] != null && !rateImages[0].isEmpty()) {
            rating.setRatingImage1(rateImages[0]);
        }
        if (rateImages.length > 1 && rateImages[1] != null && !rateImages[1].isEmpty()) {
            rating.setRatingImage2(rateImages[1]);
        }
        if (rateImages.length > 2 && rateImages[2] != null && !rateImages[2].isEmpty()) {
            rating.setRatingImage3(rateImages[2]);
        }
        if (rateImages.length > 3 && rateImages[3] != null && !rateImages[3].isEmpty()) {
            rating.setRatingImage4(rateImages[3]);
        }
    }


}