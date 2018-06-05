package com.codecool.queststore.model;

import com.codecool.queststore.details.Access;

public class Student extends Codecooler {

    public Student(String name, String surName, String login, String password) {
        super(name, surName, login, password);
        this.setAccessLevel(Access.STUDENT);
    }
}
