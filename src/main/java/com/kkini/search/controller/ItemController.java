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

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


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
            List<Item> items = itemService.autoCompleteItems(name);
            return items.stream().map(Item::getName).collect(Collectors.toList());
        }

        List<Item> items;
        if (name != null && name.length() >= 2) {
            items = itemService.searchItemsByNameOrCategory(name, categoryId);
        } else if (categoryId != null) {
            items = itemService.searchItemsByNameOrCategory(null, categoryId);
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
        Item item = itemService.getItemById(itemId);  // Fetch the item detail
        List<Ratings> ratings = ratingService.getRatingsForItem(itemId); // Fetch all ratings for the item

        Long userId = (Long) request.getSession().getAttribute("userId");

        model.addAttribute("item", item);
        model.addAttribute("ratings", ratings);
        model.addAttribute("userId", userId);

        return "itemDetail";  // Assuming the JSP name is itemDetail.jsp
    }

}