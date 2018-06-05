package com.codecool.queststore;


import com.codecool.queststore.details.Privilege;
import com.codecool.queststore.model.Codecooler;
import com.codecool.queststore.model.Mentor;
import com.codecool.queststore.model.Student;
import com.codecool.queststore.view.UI;

import java.util.List;

public class Controller {
    private Codecooler user;
    private UI UI = new UI();

    public Controller() {
        signIn();
    }

    public void signIn() {
        String login = askLogin();
        String password = askPassword();

        // TODO
        // this.user = ;
    }
    private String askLogin() {
        return UI.getInputString("Provide login");
    }

    private String askPassword() {
        return UI.askUserPassword();
    }

    public void run() {
        Privilege privilege;
        do {
            displayMenu();
            privilege = choosePrivilege();
            handleMenu(privilege);
        } while (isRun(privilege));
    }

    private Privilege choosePrivilege() {
        List<Privilege> priviledgeList = user.getAccess().getPrivileges();
        Integer answer = UI.getInputInt("Which option would you like to choose(number)");
        for (int i = 0; i < priviledgeList.size(); i++) {
            if (answer.equals(i)) {
                return priviledgeList.get(i);
            }
        }
        UI.displayLine("There's no such option!");
        return choosePrivilege();
    }

    private boolean isRun(Privilege privilege) {
        return privilege != Privilege.EXIT;
    }

    private void displayMenu() {
        UI.displayMenu(user.getAccess().getPrivileges());
    }

    private void handleMenu(Privilege privilege) {
        switch (privilege) {
            case ADD_MENTOR:
                addMentor();
                break;
            case REMOVE_MENTOR:
                removeMentor();
                break;
            case EDIT_MENTOR:
                editMentor();
                break;
            case GET_ALL_MENTORS:
                displayMentors();
                break;
            case GET_ALL_STUDENTS:
                displayStudents();
                break;
            case ADD_STUDENT:
                addStudent();
                break;
            case REMOVE_STUDENT:
                removeStudent();
                break;
            case EDIT_STUDENT:
                editStudent();
                break;
            case LOG_OUT:
                logOut();
                break;
            case EXIT:
                exit();
                break;
            default:
                errorMessage();
        }
    }

    private void logOut() {
        exit();
        signIn();
    }

    private void errorMessage() {
        UI.displayLine("You did something wrong");
    }

    private void exit() {
        UI.displayLine("Goodbye :)");
    }

    private Mentor createMentor() {
        String name;
        String surName;
        String login;
        String password;

        name = UI.getInputString("Name: ");
        surName = UI.getInputString("Surname: ");
        login = "unique login"; // TODO
        password = UI.getInputString("Password: ");

        return new Mentor(name, surName, login, password);
    }


    private void addMentor() {
    }

    private Mentor chooseMentor() {
        String login;
        login = UI.getInputString("Provide login:  ");
//        for (Mentor mentor : csvDAOEmployee.getAllMentors()) {
//            if(mentor.getLogin().equals(login)) {
//                return mentor;
//            }
//        }
        UI.displayLine("There's no such mentor");
        return chooseMentor();
    }



    private void removeMentor(){
        UI.displayLine("You are going to remove mentor: ");
//        csvDAOEmployee.removeMentor(chooseMentor());
    }

    private void editMentor() {
        String answer;
        UI.displayLine("You are going to edit mentor: ");
        Mentor mentor = chooseMentor();
        System.out.println(mentor);

        answer = UI.getInputString("Would you like to change name? (y/n)");
        if(answer.equals("y")) {
            mentor.setName(UI.getInputString("Name: "));
        }
    }

    public void displayMentors() {
//        UI.displayMentors(csvDAOEmployee.getAllMentors());
    }

    public void displayStudents() {
//        UI.displayStudents(csvDAOStudent.getAllStudent());
    }

    private Student createStudent() {
        String name;
        String surName;
        String login;
        String password;

        name = UI.getInputString("Name: ");
        surName = UI.getInputString("Surname: ");
        login = "unique login";
        password = UI.getInputString("Password: ");

        return new Student(name, surName, login, password);
    }

    private void addStudent() {
//        csvDAOStudent.addStudent(createStudent());
    }

    private Student chooseStudent() {
        String login;
        login = UI.getInputString("Provide login:  ");
//        for (Student student : csvDAOStudent.getAllStudent()) {
//            if(student.getLogin().equals(login)) {
//                return student;
//            }
//        }
        UI.displayLine("There's no such student");
        return chooseStudent();
    }

    private void removeStudent(){
        UI.displayLine("You are going to remove student: ");
//        csvDAOStudent.removeStudent(chooseStudent());
    }

    private void editStudent() {
        String answer;
        UI.displayLine("You are going to edit student: ");
        Student student = chooseStudent();
        System.out.println(student);

//        TODO
    }




}
