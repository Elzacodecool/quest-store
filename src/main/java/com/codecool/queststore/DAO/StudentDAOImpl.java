package com.codecool.queststore.DAO;

import com.codecool.queststore.model.Transaction;
import com.codecool.queststore.model.classRoom.ClassRoom;
import com.codecool.queststore.model.inventory.Category;
import com.codecool.queststore.model.inventory.Inventory;
import com.codecool.queststore.model.inventory.Item;
import com.codecool.queststore.model.user.Student;
import com.codecool.queststore.model.user.UserDetails;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAOImpl {

    private DAOFactory factory;

    public StudentDAOImpl(DAOFactory factory) {
        this.factory = factory;
    }


    public Integer add(UserDetails userDetails) {
        return factory.getUserDAO().add(userDetails);
    }
    
    public void remove(Student student) {
        factory.getUserDAO().remove(student.getUserDetails());
    }

    public void update(Student student) {
        factory.getUserDAO().update(student.getUserDetails());
    }

    public Student getStudent(Student student) {
        Student result = null;
        String query = "SELECT * FROM student INNER JOIN codecooler ON student.user_id = codecooler.id WHERE student.user_id = ?";
        try {
            ResultSet rs = factory.execQuery(query, student.getId());
            rs.next();
            UserDetails ud = factory.getUserDAO().getUser(student.getId());
            ClassRoom cr = factory.getClassDAO().getClass(rs.getInt("class_id"));
            List<Transaction> transactionDAO = factory.getTransactionDAO().getTransactionByUser(student.getId());
            result = new Student(rs.getInt("id"), ud, cr, getStudentInventory(student), transactionDAO);
        } catch (SQLException e) {
            System.out.println("Error: " + e.getErrorCode());
        }
        return result;
    }

    private Inventory getStudentInventory(Student s) {
        List<Item> items = new ArrayList<>();
        String query = "SELECT * FROM inventory INNER JOIN item ON inventory.item_id = id WHERE student_id = ?";
        try {
            ResultSet rs = factory.execQuery(query, s.getId());
            while (rs.next()) {
                items.add(
                        new Item(
                                rs.getInt("id"),
                                rs.getString("name"),
                                rs.getString("description"),
                                rs.getInt("price"),
                                new Category(rs.getString("category")
                                )
                        )
                );
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getErrorCode());
        }
        return new Inventory(s.getId(), items);
    }
}
