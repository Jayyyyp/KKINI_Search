package com.kkini.search.controller;

import com.kkini.search.entity.Item;
import com.kkini.search.service.ItemService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/items")
public class ItemController {

    private ItemService itemService;

    @Autowired
    public ItemController (ItemService itemService){
        this.itemService = itemService;
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

        if (categoryId != null && categoryId == 1L) {
            items = itemService.searchItemsByNameAndCategory(name, 1L);
            items.addAll(itemService.searchItemsByNameAndCategory(name, 2L)); // KKINI 카테고리에서 KKINI Green 상품도 보여줌
        } else if (categoryId != null && categoryId == 2L) {
            items = itemService.searchItemsByNameAndCategory(name, 2L); // KKINI Green 카테고리만 보여줌
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
}