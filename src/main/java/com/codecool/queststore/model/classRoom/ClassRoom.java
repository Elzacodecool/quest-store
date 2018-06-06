package com.codecool.queststore.model.classRoom;

public class ClassRoom {

    private int id;
    private String className;

    public ClassRoom(int id, String className) {
        this.id = id;
        this.className = className;
    }

    public int getId() {
        return id;
    }

    public String getClassName() {
        return className;
    }
}
