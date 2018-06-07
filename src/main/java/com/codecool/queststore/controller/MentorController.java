package com.codecool.queststore.controller;

import com.codecool.queststore.DAO.ItemDAO;
import com.codecool.queststore.model.inventory.Item;
import com.codecool.queststore.model.user.AccountType;
import com.codecool.queststore.view.UI;

public class MentorController {

    private MentorDAO mentorDAO;
    private ItemDAO itemDAO
    private UI ui;

    public MentorController() {
        mentorDAO = new MentorDAO();
        ui = new UI();
    }

    public void runController() {
        boolean isRunning = true;

        while (isRunning) {
            display.clearConsole();
            display.printCodecoolPersonMenu();

            int option = display.getIntInput("Type your choice: ");
            switch (option) {
                case 1:

                    break;

                case 2:

                    break;

                case 3:

                    break;

                case 4:

                    break;

                case 5:

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
                               "[2] Add new Quest",
                               "[3] Add new Artifact",
                               };

        return mentorMenu;
    }

    private void createCodecoolerAccount() {
        String first_name = ui.getInputString("Type first name: ");
        String last_name = ui.getInputString("Type last name: ");
        String email = ui.getInputString("Type email: ");
        String login = ui.getInputString("Type login name: ");
        String password = ui.getInputString("Type password: ");



    }



    private void addQuest() {
//        Item item = new Item(id, name, description, price, category);

    }

//    QUEST,
//    SINGLE_ARTIFACT,
//    GROUP_ARTIFACT
}
