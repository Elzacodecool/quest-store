package com.codecool.queststore.DAO;

import com.codecool.queststore.model.user.Mentor;
import com.codecool.queststore.model.user.User;

import java.util.List;

public interface UserDAO {

    void add(User user);
    void remove(int id);
    void update(User user);
    User getUser(int id);
    List<User> getAllStudents(Mentor mentor);
    List<User> getAll(User user);
}
