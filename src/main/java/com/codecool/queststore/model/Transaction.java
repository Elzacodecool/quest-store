package com.codecool.queststore.model;

import com.codecool.queststore.model.inventory.Item;
import com.codecool.queststore.model.user.Student;

public class Transaction {
    private int id;
    private Student student;
    private Item item;
    private int amount;

    public Transaction(int id, Student student, Item item, int amount) {
        this.id = id;
        this.student = student;
        this.item = item;
        this.amount = amount;
    }

    public Transaction(Student student, Item item, int amount) {
        this.student = student;
        this.item = item;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public Student getStudent() {
        return student;
    }

    public Item getItem() {
        return item;
    }

    public int getAmount() {
        return amount;
    }
}
