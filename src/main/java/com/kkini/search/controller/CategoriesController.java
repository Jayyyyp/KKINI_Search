package com.kkini.search.controller;

import com.kkini.search.entity.Category;
import com.kkini.search.entity.Item;
import com.kkini.search.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoriesController {

    private CategoryService categoryService;

    @Autowired
    public CategoriesController(CategoryService categoryService){
        this.categoryService = categoryService;
    }
    @GetMapping("/all/{categoryId}")
    public String getAllItemsByCategories(@PathVariable Long categoryId, Model model){
        List<Item> items = categoryService.getItemsByCategory(categoryId);
        model.addAttribute("items", items);
        return "/items";
    }
}
