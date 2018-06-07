package com.codecool.queststore.DAO;

import com.codecool.queststore.model.user.Mentor;

import java.util.List;

public interface MentorDAO {
    void add(Mentor mentor);
    void remove(Mentor mentor);
    void update(Mentor mentor);
    Mentor getMentor(int id);
    List<Mentor> getAllMentors();
}
