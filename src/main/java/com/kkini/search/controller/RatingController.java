package com.kkini.search.controller;

import com.kkini.search.entity.Item;
import com.kkini.search.entity.Ratings;
import com.kkini.search.service.ItemService;
import com.kkini.search.service.RatingService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/rate")
public class RatingController {

    private RatingService ratingService;
    private ItemService itemService;

    @Autowired
    public  RatingController(RatingService ratingService, ItemService itemService){
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
                               HttpServletRequest request,
                               @RequestParam int ratingValue,
                               @RequestParam String ratingText,
                               @RequestParam(value = "ratingImages", required = false) MultipartFile[] imageFiles,
                               Model model) {
        if (imageFiles == null) throw new IllegalArgumentException("ImageFiles cannot be null");

        Ratings rating = new Ratings();
        Long userId = (Long) request.getSession().getAttribute("userId");

        if (userId == null) return "errorPage"; // or any other error handling mechanism

        List<String> savedImagePaths = new ArrayList<>();

        for (MultipartFile imageFile : imageFiles) {
            if (imageFile != null && !imageFile.isEmpty()) {
                try {
                    String savedImagePath = ratingService.saveImage(imageFile);
                    savedImagePaths.add(savedImagePath);
                } catch (IOException e) {
                    e.printStackTrace();
                    return "errorPage";
                }
            }
        }
        ratingService.saveRating(itemId, userId, ratingValue, ratingText, savedImagePaths.toArray(new String[0]));
        model.addAttribute("rating", ratingService.getRatingsForItem(itemId));
        return "redirect:/items/" + itemId;
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

        model.addAttribute("ratingValue", rating.getRatingValue());
        model.addAttribute("ratingImage1", rating.getRatingImage1());
        model.addAttribute("ratingImage2", rating.getRatingImage2());
        model.addAttribute("ratingImage3", rating.getRatingImage3());
        model.addAttribute("ratingImage4", rating.getRatingImage4());
        model.addAttribute("rating", rating);
        model.addAttribute("ratingValue", rating.getRatingValue());

        return "editRating";
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

    @PostMapping("/edit/{ratingId}")
    public String handleEditRating(@PathVariable Long ratingId,
                                   @RequestParam int ratingValue,
                                   @RequestParam String ratingText,
                                   @RequestParam("uploadedImages") MultipartFile[] uploadedFiles,
                                   HttpServletRequest request) {

        Long userId = (Long) request.getSession().getAttribute("userId");
        Ratings existingRating = ratingService.findRatingById(ratingId);

        if (existingRating == null || !existingRating.getUsers().getUserId().equals(userId)) {
            return "errorPage";
        }

        String[] rateImages = processUploadedFiles(uploadedFiles);

        ratingService.updateRatingWithImage(ratingId, userId, ratingValue, ratingText, rateImages);

        return "redirect:/items/" + existingRating.getItemId();
    }

    private String[] processUploadedFiles(MultipartFile[] uploadedFiles) {
        String[] rateImages = new String[4];

        for (int i = 0; i < uploadedFiles.length && i < 4; i++) {
            MultipartFile file = uploadedFiles[i];
            if (file != null && !file.isEmpty()) {

                rateImages[i] = file.getOriginalFilename();
            }
        }
        return rateImages;
    }


    @PostMapping("/edit/{ratingId}/deleteImage")
    public String deleteImage(@PathVariable Long ratingId, @RequestParam String imagePath, HttpServletRequest request){
        Long userId = (Long) request.getSession().getAttribute("userId");
        Ratings rating = ratingService.findRatingById(ratingId);

        if(rating == null || !rating.getUsers().getUserId().equals(userId)){
            return "errorPage";
        }

        // DB에서 이미지 정보 삭제 및 실제 파일 삭제
        ratingService.deleteImage(imagePath);

        return "redirect:/rate/edit/" + ratingId;
    }

}
