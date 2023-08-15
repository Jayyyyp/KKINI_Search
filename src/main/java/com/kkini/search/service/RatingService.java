package com.kkini.search.service;

import com.kkini.search.entity.Ratings;
import com.kkini.search.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService {

    RatingRepository ratingRepository;

    @Autowired
    public RatingService(RatingRepository ratingRepository){
        this.ratingRepository = ratingRepository;
    }
    public List<Ratings> getRatingsForItem(Long itemId){
        return ratingRepository.findByItemId(itemId);
    }

    public void saveRating(Long itemId, String tempUserId, int ratingValue, String rateText){
        Ratings ratings = new Ratings();
        ratings.setItemId(itemId);
        ratings.setTempUserId(tempUserId);
        ratings.setRatingValue(ratingValue);
        ratings.setRatingText(rateText);

        ratingRepository.save(ratings);
    }
    public void deleteRating(Long ratingId) {
        ratingRepository.deleteById(ratingId);
    }

    public Ratings updateRating(Long ratingId, int ratingValue, String ratingText) {
        Ratings rating = ratingRepository.findById(ratingId).orElseThrow(() -> new RuntimeException("Rating not found"));
        rating.setRatingValue(ratingValue);
        rating.setRatingText(ratingText);
        return ratingRepository.save(rating);
    }
    public List<Ratings> getRatingForItemOrderedByDate(Long itemId){
        return ratingRepository.findByItemIdOrderByUpdatedAtDesc(itemId);
    }
    public boolean isAuthorizedToDelete(Long ratingId, String userId) {
        Ratings rating = ratingRepository.findById(ratingId).orElse(null);
        return rating != null && rating.getTempUserId().equals(userId);
    }

    public boolean isAuthorizedToUpdate(Long ratingId, String userId) {
        return isAuthorizedToDelete(ratingId, userId);  // 동일한 로직을 사용할 수 있다.
    }
    public Ratings findRatingById(Long ratingId) {
        return ratingRepository.findById(ratingId).orElse(null);
    }

}
