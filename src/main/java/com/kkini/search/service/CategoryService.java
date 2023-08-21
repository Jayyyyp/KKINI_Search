package com.kkini.search.service;

import com.kkini.search.entity.Category;
import com.kkini.search.entity.Item;
import com.kkini.search.repository.CategoryRepository;
import com.kkini.search.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ItemRepository itemRepository;

    // 모든 아이템을 가져오는 메서드
    public List<Item> getAllItems(){
        return itemRepository.findAll();
    }

    // 메인 카테고리(상위 카테고리가 없는 카테고리)를 가져오는 메서드
    public List<Category> getMainCategories(){
        // 메인 카테고리는 parent가 null
        return categoryRepository.findByParentIsNull();
    }
    // 주요 카테고리에 대한 모든 하위 카테고리를 가져오는 메서드
    public List<Item> getAllItemsByMainCategory(Long mainCategoryId) {
        return categoryRepository.findByParentCategoryId(mainCategoryId).stream()
                .flatMap(mainCategory -> itemRepository.findByCategory_CategoryId(mainCategory.getCategoryId()).stream())
                .collect(Collectors.toList());
        // 각 하위 카테고리에 속한 아이템들을 메서드를 통해 조회하고, 하나의 리스트로 합친다.
    }

    // 주어진 메인 카테고리 ID에 해당하는 모든 하위 카테고리를 가져오는 메서드
    public List<Category> getSubCategories(Long mainCategoryId){
        return categoryRepository.findByParentCategoryId(mainCategoryId);
        // 메인 카테고리의 모든 하위 카테고리를 조회
    }
    // 주어진 하위 카테고리 ID에 해당하는 모든 아이템을 가져오는 메서드
    public List<Item> getItemsBySubCategory(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .map(category -> Stream.concat(
                        category.getItems().stream(),
                        category.getSubCategories().stream().flatMap(subCategory ->
                                subCategory.getItems().stream())
                ).collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }
}
