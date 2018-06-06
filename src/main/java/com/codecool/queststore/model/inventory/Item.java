package com.codecool.queststore.model.inventory;


public class Item {
    private int id;
    private String name;
    private String description;
    private int price;
    private Category category;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }

    public Category getCategory() {
        return category;
    }
}
