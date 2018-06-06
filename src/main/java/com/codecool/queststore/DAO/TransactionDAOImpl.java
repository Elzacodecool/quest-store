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
        final int ID_INDEX = 1;
        final int STUDENT_ID = 2;
        final int ITEM_ID = 3;
        final int AMOUNT_ID = 4;

        try {
            int id = resultSet.getInt(ID_INDEX);
            Student student = daoFactory.getUserDAO().getUser(resultSet.getInt(STUDENT_ID));
            Item item = daoFactory.getItemDAO().get(resultSet.getInt(ITEM_ID));
            int amount = resultSet.getInt(AMOUNT_ID);

            return new Transaction(id, student, item, amount);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Transaction> getTransactionByUser(int userId) {
        ResultSet resultSet = daoFactory.execQuery("SELECT * FROM transation WHERE user_id = ?;", userId);
        return getTransactionsByResultSet(resultSet);
    }

    @Override
    public Transaction getTransaction(int id) {
        return null;
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
        String query = "DELETE FROM transaction WHERE id = ?";

        daoFactory.execQuery(query, transaction.getId());
    }

    @Override
    public void update(Transaction transaction) {

    }
}
