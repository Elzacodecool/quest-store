package com.codecool.queststore.DAO;

import com.codecool.queststore.model.classRoom.ClassRoom;

import java.util.List;

public interface ClassDAO {

    add(ClassRoom classRoom);
    remove(ClassRoom classRoom);
    update(ClassRoom classRoom);
    List<ClassRoom> getAll();
    ClassRoom getClass(int id);
    addMentor(Mentor mentor, ClassRoom classRoom);
    removeMentor(Mentor mentor, ClassRoom classRoom);
}
