package com.codecool.queststore.model.user;

public class Admin {
    private int id;
    private UserDetails userDetails;

    public Admin(int id, UserDetails userDetails) {
        this.id = id;
        this.userDetails = userDetails;
    }

    public Admin(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    public int getId() {
        return id;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }
}
