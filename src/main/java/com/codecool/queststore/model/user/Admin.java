package com.codecool.queststore.model.user;

public class Admin extends User {

    public Admin(int id, String firstName, String lastName, String email, String login, String password, AccountType accountType) {
        super(id, firstName, lastName, email, login, password, accountType);
    }
}
