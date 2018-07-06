package com.codecool.queststore.model;

import com.codecool.queststore.model.inventory.Item;

public class Transaction {
    private int id;
    private int studentId;
    private Item item;
    private int amount;

    public Transaction(int id, int student, Item item, int amount) {
        this.id = id;
        this.studentId = student;
        this.item = item;
        this.amount = amount;
    }

    public Transaction(int student, Item item, int amount) {
        this.studentId = student;
        this.item = item;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public int getStudentId() {
        return studentId;
    }

    public Item getItem() {
        return item;
    }

    public int getAmount() {
        return amount;
    }
}
