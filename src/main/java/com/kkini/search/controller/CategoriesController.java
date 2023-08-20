package com.kkini.search.controller;

import com.kkini.search.entity.Category;
import com.kkini.search.entity.Item;
import com.kkini.search.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoriesController {

    private final CategoryService categoryService;

    @GetMapping("/main") // 카테고리 전부를 보여주는 페이지
    public String displayMainCategories(Model model) {
        model.addAttribute("mainCategories", categoryService.getMainCategories());
        model.addAttribute("items", categoryService.getAllItems());
        return "/mainCategories";
    }

    @GetMapping("/main/{mainCategoryId}") // 대분류 카테고리(KKINI, KKINI Green) 분류하여 보여주는 페이지
    public String displaySubCategoriesForMainCategory(@PathVariable Long mainCategoryId, Model model) {
        model.addAttribute("subCategories", categoryService.getSubCategories(mainCategoryId));
        model.addAttribute("items", categoryService.getAllItemsByMainCategory(mainCategoryId));
        return "/subCategories";
    }
    @GetMapping("/items/{subCategoryId}") // 소분류 카테고리(식품 종류 등)을 보여주는 페이지
    public String displayItemsForSubCategory(@PathVariable Long subCategoryId, Model model) {
        model.addAttribute("items", categoryService.getItemsBySubCategory(subCategoryId));
        return "/items";
    }
}
