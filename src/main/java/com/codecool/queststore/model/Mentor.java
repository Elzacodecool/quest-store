package com.codecool.queststore.model;

import com.codecool.queststore.details.Access;

public class Mentor extends Codecooler {

    public Mentor(String name, String surName, String login, String password) {
        super(name, surName, login, password);
        this.setAccessLevel(Access.MENTOR);
    }
}
