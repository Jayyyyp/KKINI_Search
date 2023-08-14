package com.kkini.search.service;

import com.kkini.search.entity.Category;
import com.kkini.search.entity.Item;
import com.kkini.search.repository.CategoryRepository;
import com.kkini.search.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ItemRepository itemRepository;

    public List<Item> getAllItems(){
        return itemRepository.findAll();
    }

    public List<Category> getMainCategories(){
        // 메인 카테고리는 parent가 null
        return categoryRepository.findByParentIsNull();
    }
    public List<Item> getAllItemsByMainCategory(Long mainCategoryId){
        List<Category> mainCategories = categoryRepository.findByParentCategoryId(mainCategoryId);
        List<Item> items = new ArrayList<>();

        for(Category mainCategory : mainCategories){
            items.addAll(itemRepository.findByCategory_CategoryId(mainCategory.getCategoryId()));
        }
        return items;
    }
    public List<Category> getSubCategories(Long mainCategoryId){
        return categoryRepository.findByParentCategoryId(mainCategoryId);
    }
    public List<Item> getItemsBySubCategory(Long categoryId){
        return categoryRepository.findById(categoryId)
                .map(category -> {
                    List<Item> allItems = new ArrayList<>(category.getItems());
                    category.getSubCategories().forEach(subCategory -> allItems.addAll(subCategory.getItems()));
                    return allItems;
                })
                .orElse(Collections.emptyList());
    }

}
