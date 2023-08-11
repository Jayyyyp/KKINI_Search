package com.kkini.search.service;

import com.kkini.search.entity.Category;
import com.kkini.search.entity.Item;
import com.kkini.search.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Item> getItemsByCategory(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .map(Category::getItems)
                .orElse(Collections.emptyList());
    }
}
