package com.kkini.search.service;

import com.kkini.search.entity.Item;
import com.kkini.search.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;

    public List<Item> searchByName(String name){
        return itemRepository.findByNameContaining(name);
    }
}
