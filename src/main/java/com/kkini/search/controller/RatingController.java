package com.kkini.search.controller;

import com.kkini.search.entity.Ratings;
import com.kkini.search.service.RatingService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/rate")
public class RatingController {

    private RatingService ratingService;
    @Autowired
    public RatingController(RatingService ratingService){
        this.ratingService = ratingService;
    }


    @PostMapping("/rating/{itemId}")
    public String rateItem(@PathVariable Long itemId, @RequestParam int ratingValue,
                           @RequestParam String ratingText, @RequestParam Long userId) {
        Ratings savedRating = ratingService.saveRating(itemId, userId, ratingValue, ratingText);
        return "redirect:/items/" + savedRating.getItemId();
    }

    @GetMapping("/write/{itemId}")
    public String writeRating(@PathVariable Long itemId, HttpServletRequest request ,
                              Model model){
        Long userId = (Long) request.getSession().getAttribute("userId");
        model.addAttribute("userId", userId);
        model.addAttribute("itemId", itemId);
        return "writeRating";
    }

    @GetMapping("/userRatings/{userId}")
    public String getUserRatings(@PathVariable Long userId, Model model){
        List<Ratings> userRatings = ratingService.getRatingsForUser(userId);
        model.addAttribute("userRatings", userRatings);
        return "userRatings";
    }


    @PostMapping("/login")
    public String handleLogin(@RequestParam String userName, @RequestParam Long userId,
                              HttpServletRequest request){
        // 사용자 ID 저장
        request.getSession().setAttribute("userId", userId);
        return "redirect:/items/search";
    }

    // 평점 수정 페이지
    @GetMapping("/edit/{ratingId}")
    public String editRating(@PathVariable Long ratingId, Model model, HttpServletRequest request) {
        Long userId = (Long) request.getSession().getAttribute("userId");
        Ratings rating = ratingService.findRatingById(ratingId);

        if (rating == null || !rating.getUsers().getUserId().equals(userId)) {
            return "errorPage"; // 적절한 오류 페이지
        }

        model.addAttribute("rating", rating);
        return "editRating";
    }

    @GetMapping("/delete/{ratingId}")
    public String deleteRating(@PathVariable Long ratingId, HttpServletRequest request) {
        Long userId = (Long) request.getSession().getAttribute("userId");
        Ratings ratingToDelete = ratingService.findRatingById(ratingId);

        if (ratingToDelete == null || !ratingToDelete.getUsers().getUserId().equals(userId)) {
            return "errorPage"; // 적절한 오류 페이지
        }

        ratingService.deleteRating(ratingId, userId);
        return "redirect:/items/" + ratingToDelete.getItemId();
    }

}

