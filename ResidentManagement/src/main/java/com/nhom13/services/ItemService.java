package com.nhom13.services;

import com.nhom13.pojo.Item;

import java.util.List;
import java.util.Map;

public interface ItemService {
    List<Item> getAllItemById(int id, Map<String, String> params);
    Item getItemById(int itemId);
    void updatOrCreateItem(Item item);
    void deleteItem (int id);
}
