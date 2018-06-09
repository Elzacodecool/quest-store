package com.codecool.queststore.DAO;

import com.codecool.queststore.model.classRoom.ClassRoom;
import com.codecool.queststore.model.user.Mentor;

import java.util.List;

public interface ClassDAO {

    void add(ClassRoom classRoom);
    void remove(ClassRoom classRoom);
    void update(ClassRoom classRoom);
    List<ClassRoom> getAll();
    ClassRoom getClass(int id);
    List<ClassRoom> getClassesByMentor(Mentor mentor);
    void addMentor(Mentor mentor, ClassRoom classRoom);
    void removeMentor(Mentor mentor, ClassRoom classRoom);
}
