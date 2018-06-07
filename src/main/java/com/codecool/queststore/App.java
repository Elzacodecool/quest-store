package com.codecool.queststore;


import com.codecool.queststore.controller.MentorController;

public class App {
    public static void main(String[] args) {
        MentorController mentorController = new MentorController();
        mentorController.runController();
    }
}
