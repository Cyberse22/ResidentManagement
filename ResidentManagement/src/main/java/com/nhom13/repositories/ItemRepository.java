package com.nhom13.repositories;

import com.nhom13.pojo.Item;

import java.util.List;
import java.util.Map;

public interface ItemRepository {
    boolean addItem(Map<String, Item> items);
    List<Item> getAllItemById(int id, Map<String, String> params);
    Item getItemById(int itemId);
    void updateOrCreateItem(Item item);
    void deleteItem (int id);
}
