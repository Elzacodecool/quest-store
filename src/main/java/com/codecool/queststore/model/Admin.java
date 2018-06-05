package com.codecool.queststore.model;

import com.codecool.queststore.details.Access;

public class Admin extends Codecooler {

    public Admin(String name, String surName, String login, String password) {
        super(name, surName, login, password);
        this.setAccessLevel(Access.ADMIN);
    }
}
