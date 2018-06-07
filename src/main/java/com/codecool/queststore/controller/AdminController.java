package com.codecool.queststore.controller;

import com.codecool.queststore.DAO.DAOFactory;
import com.codecool.queststore.DAO.DAOFactoryImpl;
import com.codecool.queststore.DAO.MentorDAO;
import com.codecool.queststore.model.user.Mentor;

public class AdminController {
    private DAOFactory daoFactory = new DAOFactoryImpl();
    private MentorDAO mentorDAO = daoFactory.getMentorDAO();

    public void createMentor() {
        Mentor mentor = mentorDAO.getAllMentors().get(0); // new Mentor(new UserDetails("name", "lastName", "email", "login", "passwqrd"));
        mentorDAO.add(mentor);
    }

    public static void main(String[] args) {
        AdminController adminController = new AdminController();
    }
}
