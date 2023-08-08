package com.kkini.search.controller;

import com.kkini.search.entity.Item;
import com.kkini.search.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/items")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @GetMapping("/searchForm")
    public String searchForm() {
        return "searchForm";
    }

    @PostMapping("/search")
    public String search(@RequestParam String name, Model model) {
        List<Item> items = itemService.searchByName(name);
        model.addAttribute("items", items);
        return "searchResult";
    }
}
