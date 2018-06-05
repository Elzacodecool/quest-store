package com.codecool.queststore.model;

import com.codecool.queststore.details.Access;

public class Codecooler {
    private String name;
    private String surName;
    private String login;
    private String password;
    private Access accessLevel;

    public Codecooler(String name, String surName, String login, String password) {
        this.name = name;
        this.surName = surName;
        this.login = login;
        this.password = password;

    }

    public String getName() {
        return name;
    }

    public String getSurName() {
        return surName;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public Access getAccess() {
        return accessLevel;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    void setAccessLevel(Access accessLevel) {
        this.accessLevel = accessLevel;
    }

    @Override
    public String toString() {
        return "Name='" + name + '\'' +
                ", SurName='" + surName + '\'' +
                ", Login='" + login + '\'' +
                ", Password='" + password + '\'' +
                '}';
    }
}
