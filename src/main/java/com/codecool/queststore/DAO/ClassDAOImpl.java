package com.codecool.queststore.DAO;

import com.codecool.queststore.model.classRoom.ClassRoom;
import com.codecool.queststore.model.user.Mentor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClassDAOImpl implements ClassDAO {

    private DAOFactory factory;

    public ClassDAOImpl(DAOFactory factory) {
        this.factory = factory;
    }

    @Override
    public void add(ClassRoom classRoom) {
        String query = "INSERT INTO class VALUES (?)";
        factory.execQuery(query,
                classRoom.getClassName()
        );
    }

    @Override
    public void remove(ClassRoom classRoom) {
        String query = "REMOVE FROM class WHERE id = ?";
        factory.execQuery(query, classRoom.getId());
    }

    @Override
    public void update(ClassRoom classRoom) {
        String query = "UPDATE class SET name = ?";
        factory.execQuery(query, classRoom.getClassName());
    }

    @Override
    public List<ClassRoom> getAll() {
        List<ClassRoom> classes = new ArrayList<>();
        String query = "SELECT * FROM class";
        ResultSet resultSet = factory.execQuery(query);
        try {
            while (resultSet.next()) {
                classes.add(
                        new ClassRoom(
                                resultSet.getInt("id"),
                                resultSet.getString("name")
                        )
                );
            }
        } catch (SQLException e) {
            e.getErrorCode();
        }
        return classes;
    }

    @Override
    public ClassRoom getClass(int id) {
        ClassRoom classRoom = null;
        String query = "SELECT * FROM class WHERE id = ? LIMIT 1";
        ResultSet result = factory.execQuery(query, String.valueOf(id));
        try {
            classRoom = new ClassRoom(result.getInt("id"), result.getString("name"));
        } catch (SQLException e) {
            e.getErrorCode();
        }
        return classRoom;
    }

    @Override
    public void addMentor(Mentor mentor, ClassRoom classRoom) {

    }

    @Override
    public void removeMentor(Mentor mentor, ClassRoom classRoom) {

    }
}
