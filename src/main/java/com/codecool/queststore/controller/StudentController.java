package com.codecool.queststore.controller;

import com.codecool.queststore.model.user.Student;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class StudentController implements HttpHandler {

    private Student student;

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        
        String response = "";
        sendResponse(httpExchange, response);
    }

    private void sendResponse(HttpExchange httpExchange, String response) throws IOException {
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
