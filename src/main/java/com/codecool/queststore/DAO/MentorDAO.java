package com.codecool.queststore.DAO;

import com.codecool.queststore.model.classRoom.ClassRoom;
import com.codecool.queststore.model.user.Mentor;

import java.util.List;

public interface MentorDAO {
    void add(Mentor mentor);
    void remove(int id);
    void update(Mentor mentor);
    Mentor getMentor(int id);
    List<Mentor> getMentorsFromClass(ClassRoom classRoom);
    List<Mentor> getAllMentors();
}
