package com.codecool.queststore.DAO;

import com.codecool.queststore.model.Transaction;
import com.codecool.queststore.model.inventory.Item;
import com.codecool.queststore.model.user.Student;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAOImpl implements  TransactionDAO {
    private DAOFactory daoFactory;

    TransactionDAOImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public List<Transaction> getAll() {
        ResultSet resultSet = daoFactory.execQuery("SELECT * FROM transaction;");
        return getTransactionsByResultSet(resultSet);
    }

    private List<Transaction> getTransactionsByResultSet(ResultSet resultSet) {
        List<Transaction> transactions = new ArrayList<>();
        try {
            while (resultSet.next()) {
                transactions.add(getTransactionByResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    private Transaction getTransactionByResultSet(ResultSet resultSet) {
        try {
            int id = resultSet.getInt("transaction_id");
            Student student = daoFactory.getStudentDAO().getStudent(resultSet.getInt("student_id"));
            Item item = daoFactory.getItemDAO().get(resultSet.getInt("item_id"));
            int amount = resultSet.getInt("amount");

            return new Transaction(id, student, item, amount);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Transaction> getTransactionByUser(int userId) {
        ResultSet resultSet = daoFactory.execQuery("SELECT * FROM transaction WHERE user_id = ?;", userId);
        return getTransactionsByResultSet(resultSet);
    }

    @Override
    public Transaction getTransaction(int transactionId) {
        ResultSet resultSet = daoFactory.execQuery("SELECT * FROM transaction WHERE transaction_id = ?", transactionId);
        return getTransactionByResultSet(resultSet);
    }

    @Override
    public void add(Transaction transaction) {
        String query = "INSERT INTO transaction (student_id, item_id, amount) VALUES (?, ?, ?);";
        int studentId = transaction.getStudent().getId();
        int itemId = transaction.getItem().getId();
        int amount = transaction.getAmount();

        daoFactory.execQuery(query, studentId, itemId, amount);
    }

    @Override
    public void remove(Transaction transaction) {
        String query = "DELETE FROM transaction WHERE transaction_id = ?";

        daoFactory.execQuery(query, transaction.getId());
    }

    @Override
    public void update(Transaction transaction) {
        String query = "UPDATE transaction " +
                "SET student_id=?, item_id=?, amount=? " +
                "WHERE transaction_id = ? ;";
        int id = transaction.getId();
        int studentId = transaction.getStudent().getId();
        int itemId = transaction.getItem().getId();
        int amount = transaction.getAmount();

        daoFactory.execQuery(query, studentId, itemId, amount, id);
    }
}
