package com.kkini.search.service;

import com.kkini.search.entity.Category;
import com.kkini.search.entity.Item;
import com.kkini.search.repository.CategoryRepository;
import com.kkini.search.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Item> searchItemsByNameOrCategory(String name, Long categoryId) {
        if (name != null && !name.trim().isEmpty()) {
            Category category = categoryRepository.findByCategoryName(name).orElse(null);
            if (category != null) {
                if (categoryId != null) {
                    if (categoryId == 1L) { // KKINI 대분류 선택
                        return itemRepository.findByCategory_CategoryId(category.getCategoryId());
                    } else if (categoryId == 2L && category.getParent().getCategoryId() == 2L) { // KKINI Green 대분류 선택
                        return itemRepository.findByCategory_CategoryId(category.getCategoryId());
                    }
                } else {
                    return itemRepository.findByCategory_CategoryId(category.getCategoryId());
                }
            } else {
                if (categoryId != null) {
                    if (categoryId == 1L) { // KKINI 대분류 선택
                        return itemRepository.findByNameContainingOrderByAverageRatingDesc(name);
                    } else if (categoryId == 2L) { // KKINI Green 대분류 선택
                        return itemRepository.findByNameAndCategoryCategoryId(name, 2L);
                    }
                } else {
                    return itemRepository.findByNameContainingOrderByAverageRatingDesc(name);
                }
            }
        } else if (categoryId != null) {
            if (categoryId == 1L) { // KKINI 대분류 선택
                return itemRepository.findAll();
            } else if (categoryId == 2L) { // KKINI Green 대분류 선택
                return itemRepository.findByCategory_CategoryId(2L);
            }
        }
        return Collections.emptyList();
    }

    public List<Item> autoCompleteItems(String name) {
        return itemRepository.findByNameContainingOrderByAverageRatingDesc(name);
    }

    public Item getItemById(Long itemId) {
        return itemRepository.findById(itemId).orElse(null);
    }
}


