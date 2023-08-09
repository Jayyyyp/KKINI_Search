package com.kkini.search.service;

import com.kkini.search.entity.Item;
import com.kkini.search.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;

    public List<Item> searchByName(String name){
        return itemRepository.findByNameContaining(name);
    }
    public List<String> findNamesByQuery(String query) {
        return itemRepository.findByNameContainingIgnoreCase(query).stream()
                .map(Item::getName)
                .collect(Collectors.toList());
    }
    public List<String> findNamesByQueryAndCategory(String query, String category) {
        return itemRepository.findByNameContainingIgnoreCaseAndCategory(query, category)
                .stream()
                .map(Item::getName)
                .collect(Collectors.toList());
    }
}
