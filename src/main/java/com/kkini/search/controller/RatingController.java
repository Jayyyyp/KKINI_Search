package com.kkini.search.controller;

import com.kkini.search.Cookie.CookieUtils;
import com.kkini.search.entity.Ratings;
import com.kkini.search.service.RatingService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/rate")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @Autowired
    private CookieUtils cookieUtils;

    @PostMapping("/rating/{itemId}")
    public String rateItem(@PathVariable Long itemId, @RequestParam int ratingValue,
                           @RequestParam String ratingText, @RequestParam String tempUserId,
                           Model model, HttpServletResponse response) { // HttpServletResponse 추가
        ratingService.saveRating(itemId, tempUserId, ratingValue, ratingText);

        // 쿠키 설정
        cookieUtils.setCookie(response, "tempUserId", tempUserId, 60 * 60 * 24); // 1일 유효 기간

        return "redirect:/items/" + itemId;
    }

    @PutMapping("edit/{ratingId}")
    public ResponseEntity<Ratings> updateRating(@PathVariable Long ratingId, @RequestBody Ratings updatedRating, HttpServletRequest request) {
        String tempUserId = cookieUtils.getCookieValue(request, "tempUserId");
        if (tempUserId == null || !ratingService.isAuthorizedToUpdate(ratingId, tempUserId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        Ratings rating = ratingService.updateRating(ratingId, updatedRating.getRatingValue(), updatedRating.getRatingText());
        return ResponseEntity.ok(rating);
    }

    @DeleteMapping("delete/{ratingId}")
    public ResponseEntity<String> deleteRating(@PathVariable Long ratingId, HttpServletRequest request) {
        String tempUserId = cookieUtils.getCookieValue(request, "tempUserId");
        if (tempUserId == null || !ratingService.isAuthorizedToDelete(ratingId, tempUserId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Not authorized to delete this rating");
        }
        ratingService.deleteRating(ratingId);
        return ResponseEntity.ok("Rating deleted successfully");
    }
    @GetMapping("/{ratingId}")
    public ResponseEntity<Ratings> getRating(@PathVariable Long ratingId) {
        Ratings rating = ratingService.findRatingById(ratingId);
        if (rating == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(rating);
    }

}

