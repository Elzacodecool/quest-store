package com.codecool.queststore.model.inventory;

import java.util.List;

public class Inventory {
    private int student_id;
    private List<Item> items;

    public Inventory(int id, List<Item> items) {
        this.student_id = id;
        this.items = items;
    }

    public List<Item> getItems() {
        return items;
    }
}
