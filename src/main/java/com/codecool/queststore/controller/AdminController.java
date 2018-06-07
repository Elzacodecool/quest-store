package com.codecool.queststore.controller;

import com.codecool.queststore.DAO.*;
import com.codecool.queststore.model.classRoom.ClassRoom;
import com.codecool.queststore.model.user.Mentor;
import com.codecool.queststore.model.user.Student;
import com.codecool.queststore.model.user.UserDetails;

public class AdminController {
    private DAOFactory daoFactory = new DAOFactoryImpl();
    private MentorDAO mentorDAO = daoFactory.getMentorDAO();
    private ClassDAO classDAO = daoFactory.getClassDAO();
    private StudentDAO studentDAO = daoFactory.getStudentDAO();

    public void createMentor() {
        Mentor mentor = new Mentor(new UserDetails("name", "lastName", "email", "login", "passwqrd"));
        mentorDAO.add(mentor);
    }

    public void addClass() {
        ClassRoom classRoom = new ClassRoom("className");
        classDAO.add(classRoom);
    }

    public void addMentorToClass() {
        Mentor mentor = mentorDAO.getMentor(2);
        ClassRoom classRoom = classDAO.getClass(1);
        classDAO.addMentor(mentor, classRoom);
    }

    public void editMentorPassword() {
        Mentor mentor = new Mentor(new UserDetails("name", "lastName", "email", "login", "passwqrd"));
        mentorDAO.add(mentor);
        mentor.getUserDetails().setPassword("123");
        mentorDAO.update(mentor); // nie dziala !!!
    }

    public void removeMentorFromClass() {
        Mentor mentor = mentorDAO.getMentor(2);
        ClassRoom classRoom = classDAO.getClass(1);
        classDAO.removeMentor(mentor, classRoom);
    }

    public void seeMentorData() {
        Mentor mentor = mentorDAO.getMentor(1);
        for (ClassRoom classRoom : classDAO.getClassesByMentor(mentor)) {
//            for (Student student : studentDAO.getStudentsByRoom(classRoom)) { // do napisania
//                System.out.println(student);
//            }
        }
        System.out.println("mentor data: " + mentor.getUserDetails().toString());
    }

    public void addLevelOfExperience() {
        // TODO
        // student can achieve them TODO
    }


    public static void main(String[] args) {
        AdminController adminController = new AdminController();
        adminController.removeMentorFromClass();
    }
}
