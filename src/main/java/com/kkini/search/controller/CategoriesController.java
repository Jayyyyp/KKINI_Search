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

import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoriesController {

    private CategoryService categoryService;

    @Autowired
    public CategoriesController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @GetMapping("/main") // 메인 카테고리 보여주는 화면
    public String showMainCategories(Model model){
        List<Category> mainCategories = categoryService.getMainCategories();
        List<Item> items = categoryService.getAllItems();
        model.addAttribute("mainCategories", mainCategories);
        model.addAttribute("items", items);
        return "/mainCategories";
    }
    @GetMapping("/main/{mainCategoryId}") // 소분류 카테고리를 보여주는 메서드
    public String showSubCategories(@PathVariable Long mainCategoryId, Model model){
        List<Category> subCategories = categoryService.getSubCategories(mainCategoryId);
        List<Item> items = categoryService.getAllItemsByMainCategory(mainCategoryId);
        model.addAttribute("subCategories", subCategories);
        model.addAttribute("items", items);
        return "/subCategories";
    }
    @GetMapping("/items/{subCategoryId}") // 특정 소분류 카테고리의 아이템을 보여주는 메서드
    public String showItemsBySubCategory(@PathVariable Long subCategoryId, Model model){
        List<Item> items = categoryService.getItemsBySubCategory(subCategoryId);
        model.addAttribute("items", items);
        return "/items";
    }
}
