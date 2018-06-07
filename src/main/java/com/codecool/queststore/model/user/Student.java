package com.codecool.queststore.model.user;

import com.codecool.queststore.model.Transaction;
import com.codecool.queststore.model.classRoom.ClassRoom;
import com.codecool.queststore.model.inventory.Inventory;
import com.codecool.queststore.model.inventory.Item;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private int id;
    private UserDetails userDetails;
    private ClassRoom classRoom;
    private Inventory inventory;
    private List<Transaction> transactionList;

    public Student(int id, UserDetails userDetails, ClassRoom classRoom) {
        this.id = id;
        this.userDetails = userDetails;
        this.classRoom = classRoom;
        inventory = new Inventory();
        transactionList = new ArrayList<>();
    }

    public Student(int id, UserDetails userDetails, ClassRoom classRoom, Inventory inventory, List<Transaction> transactionList) {
        this.id = id;
        this.userDetails = userDetails;
        this.classRoom = classRoom;
        this.inventory = inventory;
        this.transactionList = transactionList;
    }

    public ClassRoom getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(ClassRoom classRoom) {
        this.classRoom = classRoom;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    public void buyItem() {
        //TODO
    }

    public void buyItem(Item item, int coolcoins) {
        //TODO
    }

    public void useItem(Item item) {
        //TODO
    }
}
