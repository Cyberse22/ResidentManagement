package com.nhom13.services.impl;

import com.nhom13.pojo.Item;
import com.nhom13.repositories.ItemRepository;
import com.nhom13.services.ItemService;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemRepository itemRepository;

    @Override
    public List<Item> getAllItemById(int id, Map<String, String> params) {
        return itemRepository.getAllItemById(id, params);
    }

    @Override
    public Item getItemById(int itemId) {
        return itemRepository.getItemById(itemId);
    }

    @Override
    public void updatOrCreateItem(Item item) {
        item.setCreatedDate(new Date());
        itemRepository.updateOrCreateItem(item);
    }

    @Override
    public void deleteItem(int id) {
        this.itemRepository.deleteItem(id);
    }
}
