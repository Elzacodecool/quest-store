package com.codecool.queststore.controller;

import com.codecool.queststore.DAO.*;
import com.codecool.queststore.model.classRoom.ClassRoom;
import com.codecool.queststore.model.user.Mentor;
import com.codecool.queststore.model.user.Student;
import com.codecool.queststore.model.user.UserDetails;
import com.codecool.queststore.view.UI;

import java.util.ArrayList;
import java.util.List;

public class AdminController {
    private DAOFactory daoFactory = new DAOFactoryImpl();
    private MentorDAO mentorDAO = daoFactory.getMentorDAO();
    private ClassDAO classDAO = daoFactory.getClassDAO();
    private StudentDAO studentDAO = daoFactory.getStudentDAO();

    private UI ui;

    public AdminController() {
        daoFactory = new DAOFactoryImpl();
        mentorDAO = new MentorDAOImpl(daoFactory);
        studentDAO = new StudentDAOImpl(daoFactory);
        classDAO = new ClassDAOImpl(daoFactory);
        ui = new UI();
    }

    public void runController() {
        boolean isRunning = true;

        while (isRunning) {
            ui.clearScreen();
            ui.displayArray(getMenu());

            int option = ui.getInputInt("Type your choice: ");
            switch (option) {
                case 1:
                    createMentor();
                    break;

                case 2:
                    addClass();
                    break;

                case 3:
                    addMentorToClass();
                    break;

                case 4:
                    editMentorPassword();
                    break;

                case 5:
                    removeMentorFromClass();
                    break;

                case 6:
                    seeMentorData();
                    break;

                case 7:

                    break;

                case 0:

                    isRunning = false;
                    break;
            }
        }
    }

    private String[] getMenu() {
        String[] mentorMenu = {"[1] Create mentor",
                "[2] Create class",
                "[3] Add Mentor to class",
                "[4] Edit mentor password",
                "[5] Remove mentor from class",
                "[6] Show mentor data"

        };

        return mentorMenu;
    }

    public void createMentor() {
        Mentor mentor = new Mentor(getDetailsFromInput());
        mentorDAO.add(mentor);
    }

    private UserDetails getDetailsFromInput() {
        String firstName = ui.getInputString("Type first name: ");
        String lastName = ui.getInputString("Type last name: ");
        String email = ui.getInputString("Type email: ");
        String login = ui.getInputString("Type login name: ");
        String password = ui.getInputString("Type password: ");
        return new UserDetails(firstName, lastName, email, login, password);
    }

    public void addClass() {
        String name = ui.getInputString("Type first name: ");
        ClassRoom classRoom = new ClassRoom(name);
        classDAO.add(classRoom);
    }

    public void addMentorToClass() {
        Mentor mentor = getMentorFromInput();
        ClassRoom classRoom = getClassFromInput();
        classDAO.addMentor(mentor, classRoom);
    }

    private Mentor getMentorFromInput() {
        List<Mentor> mentorList = mentorDAO.getAllMentors();
        List<String> textList = new ArrayList<>();
        for (int i = 0; i < mentorList.size(); i++) {
            UserDetails userDetails = mentorList.get(i).getUserDetails();
            textList.add(i + ". " + userDetails.getFirstName() + " " + userDetails.getLastName());
        }
        ui.displayList(textList);
        int indexMentor = ui.getInputInt("Type your choice: ");
        return mentorList.get(indexMentor);
    }

    private ClassRoom getClassFromInput() {
        List<ClassRoom> classList = classDAO.getAll();
        List<String> textList = new ArrayList<>();
        for (int i = 0; i < classList.size(); i++) {
            ClassRoom classRoom = classList.get(i);
            textList.add(i + ". " + classRoom.getClassName());
        }
        ui.displayList(textList);
        int indexClass = ui.getInputInt("Type your choice: ");
        return classList.get(indexClass);
    }

    private Mentor getMentorOfClassRoom(ClassRoom classRoom) {
        List<Mentor> mentorList = mentorDAO.getMentorsFromClass(classRoom);
        List<String> textList = new ArrayList<>();
        for (int i = 0; i < mentorList.size(); i++) {
            UserDetails userDetails = mentorList.get(i).getUserDetails();
            textList.add(i + ". " + userDetails.getFirstName() + " " + userDetails.getLastName());
        }
        ui.displayList(textList);
        int indexMentor = ui.getInputInt("Type your choice: ");
        return mentorList.get(indexMentor);
    }

    public void editMentorPassword() {
        Mentor mentor = getMentorFromInput();
        String password = ui.getInputString("Type new password: ");
        mentor.getUserDetails().setPassword(password);
        mentorDAO.update(mentor);
    }

    public void removeMentorFromClass() {
        ClassRoom classRoom = getClassFromInput();
        Mentor mentor = getMentorOfClassRoom(classRoom);
        classDAO.removeMentor(mentor, classRoom);
    }

    public void seeMentorData() {
        Mentor mentor = getMentorFromInput();
        for (ClassRoom classRoom : classDAO.getClassesByMentor(mentor)) {
            for (Student student : studentDAO.getStudentsByRoom(classRoom)) { // do napisania
                System.out.println(student.getUserDetails().getFirstName() + " " + student.getUserDetails().getLastName());
            }
        }
        System.out.println("mentor data: " + mentor.getUserDetails().getFirstName() + " " + mentor.getUserDetails().getLastName());
    }

    public void addLevelOfExperience() {
        // TODO
        // student can achieve them TODO
    }


    public static void main(String[] args) {
        AdminController adminController = new AdminController();
        adminController.runController();
    }
}
