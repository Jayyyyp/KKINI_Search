package com.kkini.search.controller;

import com.kkini.search.entity.Item;
import com.kkini.search.entity.Ratings;
import com.kkini.search.service.ItemService;
import com.kkini.search.service.RatingService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;


@Controller
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    private ItemService itemService;

    private RatingService ratingService;

    @Value("${app.image-storage-path}")
    private Path imageStroagePath; // 이미지가 저장된 경로 주입

    private static final Logger logger = LoggerFactory.getLogger(ItemController.class);

    @Autowired
    public ItemController(ItemService itemService, RatingService ratingService){
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
    @GetMapping("/images/{filename:.+}")
    public ResponseEntity<Resource> serveImage(@PathVariable String filename) {
        try {
            Path file = imageStroagePath.resolve(filename); // 이미지 경로를 결정
            logger.info("이미지 path : ", file.toString());
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" + resource.getFilename() + "\"").body(resource);
            } else {
                throw new RuntimeException("Could not read file: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }


}