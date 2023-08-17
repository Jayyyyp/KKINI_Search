package com.kkini.search.controller;

import com.kkini.search.entity.Item;
import com.kkini.search.entity.Ratings;
import com.kkini.search.service.ItemService;
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
    private ItemService itemService;
    @Autowired
    public RatingController(RatingService ratingService, ItemService itemService){
        this.ratingService = ratingService;
        this.itemService = itemService;
    }

    @GetMapping("/write/{itemId}")
    public String writeRating(@PathVariable Long itemId, HttpServletRequest request, Model model) {

        // 테스트
        request.getSession().setAttribute("userId", 1L);
        // userId 1번만 수정 및 삭제 가능 -> 성공 !!

        Long userId = (Long) request.getSession().getAttribute("userId");

        // 아이템 정보 가져오기
        Item item = itemService.getItemById(itemId);
        if (item == null) {
            return "errorPage";
        }

        model.addAttribute("userId", userId);
        model.addAttribute("itemId", itemId);
        model.addAttribute("item", item);  // 아이템 정보 추가

        return "writeRating";
    }

    @PostMapping("/rating/{itemId}")
    public String submitRating(@PathVariable Long itemId,
                               @RequestParam Long userId,
                               @RequestParam int ratingValue,
                               @RequestParam String ratingText) {
        // 평점 저장 로직 구현
        ratingService.saveRating(itemId, userId, ratingValue, ratingText);

        return "redirect:/items/" + itemId; // 아이템 상세 페이지로
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

        // 로그인 기능 구현하면 활성화
        if (rating == null || !rating.getUsers().getUserId().equals(userId)) {
            return "errorPage";
       }

        model.addAttribute("rating", rating);
        return "editRating";
    }
    @PostMapping("/edit/{ratingId}")
    public String handleEditRating(@PathVariable Long ratingId, @RequestParam int ratingValue,
                                   @RequestParam String ratingText, HttpServletRequest request) {

        Long userId = (Long) request.getSession().getAttribute("userId");
        Ratings existingRating = ratingService.findRatingById(ratingId);

        // 로그인 기능 구현하면 활성화
        if (existingRating == null || !existingRating.getUsers().getUserId().equals(userId)) {
            return "errorPage";
        }

        // 평점 수정
        ratingService.updateRating(ratingId, userId, ratingValue, ratingText);

        return "redirect:/items/" + existingRating.getItemId();
    }

    @GetMapping("/delete/{ratingId}")
    public String deleteRating(@PathVariable Long ratingId, HttpServletRequest request) {
        Long userId = (Long) request.getSession().getAttribute("userId");
        Ratings rating = ratingService.findById(ratingId, userId);

        if (!rating.getUsers().getUserId().equals(userId)) {
            throw new IllegalArgumentException("Invalid user");
        }

        ratingService.delete(rating);
        ratingService.updateAverageRating(rating.getItemId());

        return "redirect:/items/" + rating.getItemId();
    }


}

