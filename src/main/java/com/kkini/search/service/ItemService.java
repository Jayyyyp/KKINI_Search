package com.kkini.search.service;

import com.kkini.search.entity.Category;
import com.kkini.search.entity.Item;
import com.kkini.search.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository){
        this.itemRepository = itemRepository;
    }

    public List<Item> getItemsByCategoryId(Long categoryId){
        if(categoryId == 1){
            // categoryId가 1(KKINI)일 경우, 모든 아이템을 평점순으로 반환
            return itemRepository.findAll(Sort.by(Sort.Direction.DESC, "averageRating"));
        } else{
            // 그 외의 경우는 해당 카테고리에 속하는 아이템만 평점순으로 반환
            return itemRepository.findByCategory_CategoryIdOrderByAverageRatingDesc(categoryId);
        }
    }
    public List<Item> getItemsByCategoryName(String categoryName){
        return itemRepository.findByCategory_CategoryName(categoryName);
    }
    public List<Item> searchItemsByRanking(String name) {
        return itemRepository.findByNameContainingOrderByAverageRatingDesc(name);
    }
    public List<Item> searchAllItemsByName(String name) {
        return itemRepository.findAllByNameLike(name);
    }
    public List<Item> searchItemsByNameAndCategory(String name, Long categoryId) {
        return itemRepository.findByNameAndCategoryCategoryId(name, categoryId);
    }
    public List<Item> autoCompleteItems(String searchTerm){
        return itemRepository.findByNameContainingOrderByAverageRatingDesc(searchTerm);
    }
}

