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
    public List<Item> searchItemsByRanking(String name) {
        return itemRepository.findByNameContainingOrderByAverageRatingDesc(name);
    }
    public List<Item> searchItemsByNameAndCategory(String name, Long categoryId) {
        return itemRepository.findByNameAndCategoryCategoryId(name, categoryId);
    }
    public List<Item> autoCompleteItems(String searchTerm){
        return itemRepository.findByNameContainingOrderByAverageRatingDesc(searchTerm);
    }
}

