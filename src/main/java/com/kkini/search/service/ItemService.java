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
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Item> searchItemsByName(String name, Long categoryId) {
        if (name != null && !name.trim().isEmpty()) {
            List<Category> categories = categoryRepository.findByCategoryNameContaining(name);
            if (!categories.isEmpty()) {
                Category category = categories.get(0);

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

    public Item getItemById(Long itemId) {
        return itemRepository.findById(itemId).orElse(null);
    }

    public List<String> autoCompleteNames(String name) {
        List<String> names = new ArrayList<>();

        if (name != null && name.length() >= 2) {
            List<Item> items = itemRepository.findByNameContainingOrderByAverageRatingDesc(name);
            List<Category> categories = categoryRepository.findByCategoryNameContaining(name);

            names.addAll(items.stream().map(Item::getName).collect(Collectors.toList()));
            names.addAll(categories.stream().map(Category::getCategoryName).collect(Collectors.toList()));
        }

        return names;
    }

}


