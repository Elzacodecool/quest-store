package com.codecool.queststore.controller;

import com.codecool.queststore.DAO.ClassDAO;
import com.codecool.queststore.DAO.DAOFactory;
import com.codecool.queststore.DAO.DAOFactoryImpl;
import com.codecool.queststore.DAO.MentorDAO;
import com.codecool.queststore.model.classRoom.ClassRoom;
import com.codecool.queststore.model.user.Mentor;
import com.codecool.queststore.model.user.UserDetails;

public class AdminController {
    private DAOFactory daoFactory = new DAOFactoryImpl();
    private MentorDAO mentorDAO = daoFactory.getMentorDAO();
    private ClassDAO classDAO = daoFactory.getClassDAO();

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

    public void editMentor() {
        Mentor mentor = new Mentor(new UserDetails("name", "lastName", "email", "login", "passwqrd"));
        mentorDAO.add(mentor);
        mentor.getUserDetails().setPassword("123");
        mentorDAO.update(mentor);
    }

    public static void main(String[] args) {
        AdminController adminController = new AdminController();
        adminController.editMentor();
    }
}
