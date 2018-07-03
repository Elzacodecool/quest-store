package com.codecool.queststore;


import com.codecool.queststore.controller.AccountController;
import com.codecool.queststore.controller.AdminController;
import com.codecool.queststore.controller.MentorController;
import com.codecool.queststore.controller.StudentController;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class App {
    public static void main(String[] args) {
        HttpServer httpServer = null;
        try {
            httpServer = HttpServer.create(new InetSocketAddress(8000), 0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        httpServer.createContext("/index", new AccountController());
        httpServer.createContext("/admin", new AdminController());
        httpServer.createContext("/mentor", new MentorController());
        httpServer.createContext("/student", new StudentController());
        httpServer.createContext("/static", new Static());

        httpServer.setExecutor(null);

        httpServer.start();
    }
}
