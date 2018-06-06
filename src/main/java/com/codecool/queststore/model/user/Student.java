package com.codecool.queststore.model.user;

import java.util.ArrayList;
import java.util.List;

public class Student extends User {

    private ClassRoom classRoom;
    private Inventory inventory;
    private List<Transaction> transactionList;

    public Student(int id, String firstName, String lastName, String email, String login, String password, AccountType accountType, ClassRoom classRoom) {
        super(id, firstName, lastName, email, login, password, accountType);
        this.classRoom = classRoom;
        inventory = new Inventory();
        transactionList = new ArrayList<>();
    }

    public Student(int id, String firstName, String lastName, String email, String login, String password, AccountType accountType, ClassRoom classRoom, Inventory inventory, List<Transaction> transactionList) {
        super(id, firstName, lastName, email, login, password, accountType);
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
