package com.codecool.queststore.DAO;

import com.codecool.queststore.model.Transaction;

import java.util.List;

public interface TransactionDAO {
    void add(Transaction transaction);
    void remove(Transaction transaction);
    void update(Transaction transaction);
    List<Transaction> getAll();
    Transaction getTransaction(int id);
    List<Transaction> getTransactionByUser(int id);
}
