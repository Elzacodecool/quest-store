package com.codecool.queststore.model.user;

public class Mentor {
    private int id;
    private UserDetails userDetails;

    public Mentor(int id, UserDetails userDetails) {
        this.id = id;
        this.userDetails = userDetails;
    }

    public Mentor(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    public int getId() {
        return id;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }
}
