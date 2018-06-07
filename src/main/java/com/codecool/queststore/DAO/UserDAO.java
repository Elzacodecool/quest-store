package com.codecool.queststore.DAO;

import com.codecool.queststore.model.user.Mentor;
import com.codecool.queststore.model.user.UserDetails;

import java.util.List;

public interface UserDAO {

    void add(UserDetails userDetails);
    void remove(int id);
    void update(UserDetails userDetails);
    UserDetails getUser(int id);
    List<UserDetails> getAllStudents(Mentor mentor);
    List<UserDetails> getAll();
}
