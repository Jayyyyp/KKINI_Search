package com.kkini.search.controller;

import com.kkini.search.entity.Item;
import com.kkini.search.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/items")
public class ItemController {

    private ItemService itemService;

    @Autowired
    public ItemController (ItemService itemService){
        this.itemService = itemService;
    }

    @GetMapping("/search")
    public String searchItems(@RequestParam(required = false) String name,
                              @RequestParam(required = false) Long categoryId,
                              Model model) {
        List<Item> items;

        if (categoryId != null && categoryId == 1L) {
            // KKINI 카테고리 선택 시 전체 상품 검색
            items = itemService.searchItemsByNameAndCategory(name, categoryId);
        } else if (categoryId != null && categoryId == 2L) {
            // KKINI Green 카테고리 선택 시 해당 카테고리 상품만 검색
            items = itemService.searchItemsByNameAndCategory(name, categoryId);
        } else {
            // 카테고리 선택이 없는 경우
            return "search"; // 혹은 다른 로직을 적용하시면 됩니다.
        }
        model.addAttribute("items", items);

        if (!items.isEmpty()) {
            return "searchResults";
        }else {
            return "noItems";
        }

    }

}
