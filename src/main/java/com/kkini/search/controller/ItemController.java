package com.kkini.search.controller;

import com.kkini.search.entity.Item;
import com.kkini.search.entity.Ratings;
import com.kkini.search.service.ItemService;
import com.kkini.search.service.RatingService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;


@Controller
@RequestMapping("/items")
public class ItemController {

    private ItemService itemService;

    private RatingService ratingService;

    @Autowired
    public ItemController (ItemService itemService, RatingService ratingService){
        this.itemService = itemService;
        this.ratingService = ratingService;
    }

    // 검색 기능 구현
    @GetMapping("/search")
    public Object searchItems(@RequestParam(required = false) String name,
                              @RequestParam(required = false) Long categoryId,
                              Model model, HttpServletRequest request) {

        if (isAjaxRequest(request)) {
            List<String> autoCompletes = itemService.autoCompleteNames(name);
            return autoCompletes;
        }

        List<Item> items;
        if (name != null && name.length() >= 2) {
            items = itemService.searchItemsByName(name, categoryId);
        } else if (categoryId != null) {
            items = itemService.searchItemsByName(null, categoryId);
        } else {
            model.addAttribute("items", Collections.emptyList());
            return "search";
        }

        model.addAttribute("items", items);
        if (!items.isEmpty()) {
            return "searchResults";
        } else {
            return "noItems";
        }
    }

    private boolean isAjaxRequest(HttpServletRequest request) {
        String requestedWithHeader = request.getHeader("X-Requested-With");
        return "XMLHttpRequest".equals(requestedWithHeader);
    }

    @GetMapping("/{itemId}")
    public String getItemDetail(@PathVariable Long itemId, Model model, HttpServletRequest request) {
        Item item = itemService.getItemById(itemId);
        if (item == null) {
            return "errorPage";
        }
        List<Ratings> ratings = ratingService.getRatingsForItem(itemId);
        Long userId = (Long) request.getSession().getAttribute("userId");

        model.addAttribute("item", item);
        model.addAttribute("ratings", ratings);
        model.addAttribute("userId", userId);

        return "itemDetail";
    }
    @GetMapping("/{imageName}.png")
    public ResponseEntity<Resource> fetchImage(@PathVariable String imageName) {
        Path imagePath = Paths.get("your-image-storage-directory", imageName + ".png");

        if (Files.exists(imagePath)) {
            Resource resource = new FileSystemResource(imagePath);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}