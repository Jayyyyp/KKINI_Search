package com.kkini.search.service;

import com.kkini.search.entity.Ratings;
import com.kkini.search.entity.Users;
import com.kkini.search.repository.ItemRepository;
import com.kkini.search.repository.RatingRepository;
import com.kkini.search.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService {

    private final RatingRepository ratingRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    @Autowired
    public RatingService(RatingRepository ratingRepository,
                         UserRepository userRepository,
                         ItemRepository itemRepository) {
        this.ratingRepository = ratingRepository;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
    }

    public List<Ratings> getRatingForItemOrderedByDate(Long itemId) {
        return ratingRepository.findByItemIdOrderByUpdatedAtDesc(itemId);
    }

    public List<Ratings> getRatingsForItem(Long itemId){
        return ratingRepository.findByItemId(itemId);
    }

    public Ratings saveRating(Long itemId, Long tempUserId, int ratingValue, String rateText) {
        Ratings ratings = new Ratings();
        Users users = userRepository.findById(tempUserId).orElse(null);
        ratings.setItemId(itemId);
        ratings.setUsers(users);
        ratings.setRatingValue(ratingValue);
        ratings.setRatingText(rateText);

        Ratings savedRating = ratingRepository.save(ratings);  // 저장된 평점 객체를 저장

        updateAverageRating(itemId);

        return savedRating;  // 저장된 평점 객체를 반환
    }

    private void updateAverageRating(Long itemId) {
        List<Ratings> ratings = ratingRepository.findByItemId(itemId);
        double sum = 0;
        for (Ratings rating : ratings) {
            sum += rating.getRatingValue();
        }
        double average = sum / ratings.size();

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
    }

    public void deleteRating(Long ratingId, Long userId) {
        Ratings rating = ratingRepository.findById(ratingId).orElse(null);

        if (rating == null || !rating.getUsers().getUserId().equals(userId)) {
            throw new IllegalArgumentException("Invalid rating or user");
        }

        ratingRepository.delete(rating);
    }
}
