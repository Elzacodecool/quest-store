package com.codecool.queststore.DAO;

import com.codecool.queststore.model.inventory.Item;

import java.util.List;

public interface ItemDAO {
    void add(Item item);
    void remove(Item item);
    void update(Item item);
    Item get(int id);
    List<Item> getItems();
    List<Item> getQuests();
}
