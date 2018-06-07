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

public class StudentDAOImpl implements StudentDAO {

    private DAOFactory factory;

    public StudentDAOImpl(DAOFactory factory) {
        this.factory = factory;
    }


    public Integer add(Student student) {
        return factory.getUserDAO().add(student.getUserDetails());
    }
    
    public void remove(Student student) {
        factory.getUserDAO().remove(student.getUserDetails());
    }

    public void update(Student student) {
        factory.getUserDAO().update(student.getUserDetails());
    }

    public Student getStudent(int id) {
        Student result = null;
        String query = "SELECT * FROM student INNER JOIN codecooler ON student.user_id = codecooler.id WHERE student.user_id = ?";
        try {
            ResultSet rs = factory.execQuery(query, id);
            rs.next();
            UserDetails ud = factory.getUserDAO().getUser(id);
            ClassRoom cr = factory.getClassDAO().getClass(rs.getInt("class_id"));
            List<Transaction> transactionDAO = factory.getTransactionDAO().getTransactionByUser(id);
            result = new Student(rs.getInt("id"), ud, cr, getStudentInventory(id), transactionDAO);
        } catch (SQLException e) {
            System.out.println("Error: " + e.getErrorCode());
        }
        return result;
    }

    private Inventory getStudentInventory(int id) {
        List<Item> items = new ArrayList<>();
        String query = "SELECT * FROM inventory INNER JOIN item ON inventory.item_id = item.item_id " +
                "WHERE inventory.student_id = ?;";
        try {
            ResultSet rs = factory.execQuery(query, id);
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
        return new Inventory(id, items);
    }
}
