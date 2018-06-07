package com.codecool.queststore.DAO;

import com.codecool.queststore.model.classRoom.ClassRoom;
import com.codecool.queststore.model.user.Student;

import java.util.List;

public interface StudentDAO {
    Integer add(Student student);
    void remove(int idi);
    void update(Student student);
    Student getStudent(int id);
//    List<Student> getStudentsByRoom(ClassRoom classRoom);
//    List<Student> getAllStudents(Student student);
}
