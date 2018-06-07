package com.codecool.queststore;

import com.codecool.queststore.DAO.DAOFactory;
import com.codecool.queststore.DAO.DAOFactoryImpl;
import com.codecool.queststore.DAO.TransactionDAO;
import com.codecool.queststore.model.Transaction;
import com.codecool.queststore.model.classRoom.ClassRoom;
import com.codecool.queststore.model.inventory.Category;
import com.codecool.queststore.model.inventory.Item;
import com.codecool.queststore.model.user.AccountType;
import com.codecool.queststore.model.user.Student;
import com.codecool.queststore.model.user.UserDetails;

public class App {
    public static void main(String[] args) {
        DAOFactory daoFactory = new DAOFactoryImpl();
        Student student = new Student(new UserDetails(1,"name", "lastName", "email", "login", "password"), new ClassRoom(1, "webRoom"));
        Item item = new Item(1, "name", "description", 100, Category.SINGLE_ARTIFACT);
        Transaction transaction = new Transaction(2, student, item, 10);
        TransactionDAO transactionDAO = daoFactory.getTransactionDAO();
        transactionDAO.update(transaction);
//        System.out.println(transactionDAO.getAll().size());
    }
}
