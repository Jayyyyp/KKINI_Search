package com.kkini.search.service;

import com.kkini.search.entity.Item;
import com.kkini.search.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository){
        this.itemRepository = itemRepository;
    }


    public List<Item> searchItemsByNameAndCategory(String name, Long categoryId) {
        return itemRepository.findByNameAndCategoryCategoryId(name, categoryId);
    }
    public List<Item> autoCompleteItems(String searchTerm){
        return itemRepository.findByNameContainingOrderByAverageRatingDesc(searchTerm);
    }
}

