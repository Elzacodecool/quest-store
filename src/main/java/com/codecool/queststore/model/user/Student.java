package com.codecool.queststore.model.user;

import com.codecool.queststore.DAO.DAOFactory;
import com.codecool.queststore.DAO.DAOFactoryImpl;
import com.codecool.queststore.DAO.TransactionDAO;
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

    public Student(UserDetails userDetails, ClassRoom classRoom, DAOFactory daoFactory) {
        this.userDetails = userDetails;
        this.classRoom = classRoom;
        inventory = new Inventory(id);
        transactionList = setTransactions(daoFactory);
    }

    public Student(int id, UserDetails userDetails, ClassRoom classRoom, Inventory inventory, List<Transaction> transactionList) {
        this.id = id;
        this.userDetails = userDetails;
        this.classRoom = classRoom;
        this.inventory = inventory;
        this.transactionList = transactionList;
    }

    private List<Transaction> setTransactions(DAOFactory daoFactory) {
        return daoFactory.getTransactionDAO().getTransactionByUser(userDetails.getId());
    }

    public int getId() {
        return id;
    }

    public UserDetails getUserDetails() {
        return userDetails;
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

    public int getCash() {
        int cash = 0;
        for (Transaction transaction : transactionList) {
            cash += transaction.getAmount();
        }
        return cash;
    }

    public void buyItem(Item item) {
        DAOFactory daoFactory = new DAOFactoryImpl();
        TransactionDAO transactionDAO = daoFactory.getTransactionDAO();
        Transaction transaction = new Transaction(id, item, item.getPrice());
        transactionDAO.add(transaction);
        //NEED TO ADD TO INVENTORY. INVENTORY DOES NOT EXISTS TODO
    }

    public void useItem(Item item) {
        //TODO
    }
}
