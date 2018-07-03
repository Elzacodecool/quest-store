package com.codecool.queststore.controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;
import java.io.OutputStream;

public class AdminController implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String response = getResponse();
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private String getResponse() {
        JtwigTemplate jtwigTemplate = JtwigTemplate.classpathTemplate("templates/menu-admin.twig");
        JtwigModel jtwigModel = JtwigModel.newModel();
        String response = jtwigTemplate.render(jtwigModel);

        return response;
    }
}
