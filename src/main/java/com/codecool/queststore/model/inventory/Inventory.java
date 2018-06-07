package com.codecool.queststore.model.inventory;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private int studentId;
    private List<Item> items;

    public Inventory(int studentId, List<Item> items) {
        this.studentId = studentId;
        this.items = items;
    }

    public Inventory(int studentId) {
        this.studentId = studentId;
        this.items = new ArrayList<>();
    }

    public List<Item> getItems() {
        return items;
    }
}
