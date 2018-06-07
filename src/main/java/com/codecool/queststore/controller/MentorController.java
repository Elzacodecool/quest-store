package com.codecool.queststore.controller;

import com.codecool.queststore.DAO.*;
import com.codecool.queststore.model.classRoom.ClassRoom;
import com.codecool.queststore.model.inventory.Category;
import com.codecool.queststore.model.inventory.Item;
import com.codecool.queststore.model.user.Student;
import com.codecool.queststore.model.user.UserDetails;
import com.codecool.queststore.view.UI;

import java.util.List;

public class MentorController {

    private MentorDAO mentorDAO;
    private StudentDAO studentDAO;
    private ItemDAO itemDAO;
    private ClassDAO classDAO;
    private UI ui;

    public MentorController() {
        mentorDAO = new MentorDAOImpl();
        itemDAO = new ItemDAOImpl();
        studentDAO = new StudentDAOImpl();
        classDAO = new ClassDAOImpl();
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
                    createCodecoolerAccount();
                    break;

                case 2:
                    addItem();
                    break;

                case 3:

                    break;

                case 4:
                    addNewQuest();
                    break;

                case 5:
                    addNewArtefact();
                    break;

                case 6:

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
        String[] mentorMenu = {"[1] Create codecooler account",
                               "[2] Add Item",
                               "[3] Update Item",
                               "[4] Add Quest",
                               "[5] Add Artefact",

                               };

        return mentorMenu;
    }

    private void createCodecoolerAccount() {
        int id = 0;
        String firstName = ui.getInputString("Type first name: ");
        String lastName = ui.getInputString("Type last name: ");
        String email = ui.getInputString("Type email: ");
        String login = ui.getInputString("Type login name: ");
        String password = ui.getInputString("Type password: ");
        UserDetails userDetails = new UserDetails(id, firstName, lastName, email, login, password);
        ClassRoom classRoom = chooseClassRoom();
        Student student = new Student(userDetails, classRoom);
        studentDAO.add(student);
    }



    private void addItem() {
        int id = 0;
        String name = ui.getInputString("Type name of Quest: ");
        String description = ui.getInputString("Type description of Quest: ");
        int price = ui.getInputInt("Type price of Quest: ");
        String category = chooseCategory();
        Item item = new Item(id, name, description, price, new Category(category));
        itemDAO.add(item);
    }

    private String chooseCategory() {
        String[] menuCategories = {"[1] Quest", "[2] Single Artifact", "[3] Group Artifact"};
        boolean isRunning = true;
        String category = "";

        while (isRunning) {
            ui.displayArray(menuCategories);

            int option = ui.getInputInt("Type your choice: ");
            switch (option) {
                case 1:
                    category = "Quest";
                    isRunning = false;
                    break;

                case 2:
                    category = "Single Artifact";
                    isRunning = false;
                    break;

                case 3:
                    category = "Group Artifact";
                    isRunning = false;
                    break;
            }
        }
        return category;
    }

    private ClassRoom chooseClassRoom() {
        List<ClassRoom> classRoomList = classDAO.getAll();
        String[] className = new String[classRoomList.size()];
        for (int i = 0; i < classRoomList.size(); i++) {
            className[i] = "[" + i + "] " + classRoomList.get(i).getClassName();
        }
        int indexClass = ui.getInputInt("Type your choice: ");
        return classRoomList.get(indexClass);
    }

    private void addNewQuest() {
        Item item = new Item(0, "Demo day", "Presentation on demo day", 50, new Category("Quest"));
    }

    private void addNewArtefact() {
        Item item = new Item(0, "Sanctuary", "The student can spend a day off", -50, new Category("Single Artifact"));
    }
}
