package com.codecool.queststore.controller;

import com.codecool.queststore.DAO.*;
import com.codecool.queststore.model.Transaction;
import com.codecool.queststore.model.classRoom.ClassRoom;
import com.codecool.queststore.model.inventory.Category;
import com.codecool.queststore.model.inventory.Item;
import com.codecool.queststore.model.user.Student;
import com.codecool.queststore.model.user.UserDetails;
import com.codecool.queststore.view.UI;

import java.util.ArrayList;
import java.util.List;

public class MentorController {

    private MentorDAO mentorDAO;
    private StudentDAO studentDAO;
    private ItemDAO itemDAO;
    private ClassDAO classDAO;
    private DAOFactory daoFactory;
    private TransactionDAO transactionDAO;
    private UI ui;

    public MentorController() {
        daoFactory = new DAOFactoryImpl();
        mentorDAO = new MentorDAOImpl(daoFactory);
        itemDAO = new ItemDAOImpl(daoFactory);
        studentDAO = new StudentDAOImpl(daoFactory);
        classDAO = new ClassDAOImpl(daoFactory);
        transactionDAO = new TransactionDAOImpl(daoFactory);
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
                    addQuest();
                    break;

                case 3:
                    addArtifact();
                    break;

                case 4:
                    updateQuest();
                    break;

                case 5:
                    updateArtifact();
                    break;

                case 6:
                    addQuestToStudent();
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
        String[] mentorMenu = {"[1] Create student account",
                               "[2] Add Quest",
                               "[3] Add Artifact",
                               "[4] Update Quest",
                               "[5] Update Artifact",
                               "[6] Add Quest to student"
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



    private void addItem(Category category) {
        int id = 0;
        String name = ui.getInputString("Type name of Quest: ");
        String description = ui.getInputString("Type description of Quest: ");
        int price = ui.getInputInt("Type price of Quest: ");
        Item item = new Item(id, name, description, price, category);
        itemDAO.add(item);
    }

    private String chooseCategory() {
        String[] menuCategories = {"[1] Single Artifact", "[2] Group Artifact"};
        boolean isRunning = true;
        String category = "";

        while (isRunning) {
            ui.displayArray(menuCategories);

            int option = ui.getInputInt("Type your choice: ");
            switch (option) {
                case 1:
                    category = "Single Artifact";
                    isRunning = false;
                    break;

                case 2:
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
            ui.displayLine(className[i] = "[" + i + "] " + classRoomList.get(i).getClassName());
        }
        int indexClass = ui.getInputInt("Type your choice: ");
        return classRoomList.get(indexClass);
    }

    private void addQuest() {
        Category category = new Category("Quest");
        addItem(category);
    }

    private void addArtifact() {
        String categoryName = chooseCategory();
        addItem(new Category(categoryName));
    }



    private Item getItem(List<Item> itemList) {
        List<String> textList = new ArrayList<>();
        String line;
        for (int i = 0; i < itemList.size(); i++) {
            line = i + ". " + itemList.get(i).getName();
            textList.add(line);
        }
        ui.displayList(textList);
        int indexItem = ui.getInputInt("Type your choice: ");
        return itemList.get(indexItem);
    }

    private void updateQuest() {
        List<Item> itemList = itemDAO.getQuests();
        Item item = getItem(itemList);
        updateItem(item);
    }

    private void updateArtifact() {
        List<Item> itemList = itemDAO.getArtifact();
        Item item = getItem(itemList);
        updateItem(item);
    }

    private void updateItem(Item item) {
        String name = ui.getInputString("Type name: ");
        String description = ui.getInputString("Type description of Quest: ");
        int price = ui.getInputInt("Type price of Quest: ");
        item.setName(name);
        item.setDescription(description);
        item.setPrice(price);
        itemDAO.update(item);

    }

    private void addQuestToStudent() {
        List<Item> itemList = itemDAO.getQuests();
        Item item = getItem(itemList);
        Student student = chooseStudent();
        Transaction transaction = new Transaction(student, item, item.getPrice());
        transactionDAO.add(transaction);

    }

    private Student chooseStudent() {
        List<Student> studentList = studentDAO.getAllStudents();
        System.out.println(studentList.size());
        for (int i = 0; i < studentList.size(); i++) {
            UserDetails us = studentList.get(i).getUserDetails();
            ui.displayLine("[" + i + "] " + us.getFirstName() + " " + us.getLastName());
        }
        int indexStudent = ui.getInputInt("Type your choice: ");
        return studentList.get(indexStudent);
    }

    public static void main(String[] args) {
        MentorController mentorController = new MentorController();
        mentorController.runController();
    }
}