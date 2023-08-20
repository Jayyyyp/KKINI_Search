package com.kkini.search.service;

import com.kkini.search.entity.Category;
import com.kkini.search.entity.Item;
import com.kkini.search.repository.CategoryRepository;
import com.kkini.search.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {
    // 비즈니스 로직과 데이터 처리 로직을 포함한다.

    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;

    // 이름과 카테고리 ID를 기반으로 아이템을 검색
    public List<Item> searchItemsByName(String name, Long categoryId) {
        if (name != null && !name.trim().isEmpty()) { // 이름이 주어지면,
            // 해당 이름을 포함하는 카테고리를 먼저 찾는다
            List<Category> categories = categoryRepository.findByCategoryNameContaining(name);

            if (!categories.isEmpty()) { // 카테고리가 있으면,
                return handleCategorySearch(categories.get(0), categoryId);
            } else { // 카테고리가 없으면,
                return handleNameSearch(name, categoryId);
            }
        } else if (categoryId != null) { // 카테고리 ID만 주어지면,
            return handleCategoryOnlySearch(categoryId);
        }
        return Collections.emptyList();
    }

    // 주어진 카테고리와 카테고리 ID를 기반으로 아이템을 검색
    private List<Item> handleCategorySearch(Category category, Long categoryId) {
        if (categoryId == null) {
            return itemRepository.findByCategory_CategoryId(category.getCategoryId());
        }
        switch (categoryId.intValue()) { // 카테고리 ID에 따라 다른 로직을 수행
            case 1: // KKINI
                return itemRepository.findByCategory_CategoryId(category.getCategoryId());
            case 2: // KKINI Green
                if (category.getParent().getCategoryId() == 2L) {
                    return itemRepository.findByCategory_CategoryId(category.getCategoryId());
                }
                break;
        }
        return Collections.emptyList();
    }

    // 주어진 이름과 카테고리 ID를 기반으로 아이템을 검색
    private List<Item> handleNameSearch(String name, Long categoryId) {
        if (categoryId == null) {
            return itemRepository.findByNameContainingOrderByAverageRatingDesc(name);
        }
        switch (categoryId.intValue()) { // 카테고리 ID에 따라 다른 로직을 수행
            case 1: // KKINI
                return itemRepository.findByNameContainingOrderByAverageRatingDesc(name);
            case 2: // KKINI Green
                return itemRepository.findByNameAndCategoryCategoryId(name, 2L);
        }
        return Collections.emptyList();
    }

    // 주어진 아이템 ID를 기반으로 아이템을 검색
    private List<Item> handleCategoryOnlySearch(Long categoryId) {
        switch (categoryId.intValue()) { // 카테고리 ID에 따라 다른 로직을 수행
            case 1: // KKINI
                return itemRepository.findAll();
            case 2: // KKINI Green
                return itemRepository.findByCategory_CategoryId(2L);
        }
        return Collections.emptyList();
    }

    // 주어진 아이템 ID를 기반으로 아이템을 검색
    public Item getItemById(Long itemId) {
        // 아이템이 있으면 반환하고, 없으면 null 반환
        return itemRepository.findById(itemId).orElse(null);
    }

    // 주어진 이름을 기반으로 자동완성 목록을 제공
    public List<String> autoCompleteNames(String name) {
        if (name == null || name.length() < 2) { // 이름이 2글자 이상일때만 자동완성 검색을 수행
            return Collections.emptyList();
        }

        // 이름이 주어진 문자열(name)이 포함된 아이템들을 평균 평점 내림차순으로 결정한다. 아이템 객체의 리스트가 반환된다.
        List<String> itemNames = itemRepository.findByNameContainingOrderByAverageRatingDesc(name)
                .stream() // 반환된 아이템 객체 리스트를 스트림으로 변환
                .map(Item::getName) // 각 아이템 객체에서 이름만 추출
                .collect(Collectors.toList()); // 스트림의 내용을 리스트로 변환한다.
                // itemNames 리스트는 검색된 아이템의 이름들만 저장된다.

        // 이름이 주어진 문자열(name)이 포함된 카테고리들을 검색한다. 카테고리 객체의 리스트가 반환된다.
        List<String> categoryNames = categoryRepository.findByCategoryNameContaining(name)
                .stream() // 반환된 카테고리 리스트를 스트림으로 변환
                .map(Category::getCategoryName) // 각 카테고리 객체에서 이름만 추출
                .collect(Collectors.toList()); // 스트림의 내용을 리스트로 변환한다.
                // categoryNames 리스트는 검새된 카테고리의 이름들만 저장된다.

        List<String> combinedNames = new ArrayList<>();
        combinedNames.addAll(itemNames);
        combinedNames.addAll(categoryNames);

        // 아이템 이름과 카테고리 이름을 모두 검색해 합친 후, 반환한다.
        return combinedNames;
    }

}

