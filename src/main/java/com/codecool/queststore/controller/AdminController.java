package com.codecool.queststore.controller;

import com.codecool.queststore.DAO.AdminDAO;
import com.codecool.queststore.DAO.AdminDAOImpl;
import com.codecool.queststore.DAO.DAOFactory;
import com.codecool.queststore.DAO.DAOFactoryImpl;
import com.codecool.queststore.model.SingletonAcountContainer;
import com.codecool.queststore.model.user.Admin;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpCookie;

public class AdminController implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String response = getResponse(httpExchange);
        sendResponse(httpExchange, response);
    }

    private String getResponse(HttpExchange httpExchange) {

        Admin admin = getAdminByCookie(httpExchange);
        JtwigTemplate jtwigTemplate = JtwigTemplate.classpathTemplate("templates/menu-admin.twig");
        JtwigModel jtwigModel = JtwigModel.newModel();
        jtwigModel.with("fullname", admin.getUserDetails().getFirstName() + " " + admin.getUserDetails().getLastName());
        String response = jtwigTemplate.render(jtwigModel);

        return response;
    }

    private Admin getAdminByCookie(HttpExchange httpExchange) {
        String sessionId = getSessionId(httpExchange);
        int codecoolerId = getCodecoolerId(sessionId);
        AdminDAOImpl adminDAO = getAdminDao();
        return adminDAO.getAdmin(codecoolerId);
    }

    private String getSessionId(HttpExchange httpExchange) {
        HttpCookie httpCookie;
        String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");
        httpCookie = HttpCookie.parse(cookieStr).get(0);
        return httpCookie.toString().split("=")[1];
    }

    private int getCodecoolerId(String sessionId) {
        SingletonAcountContainer sessionsIDs = SingletonAcountContainer.getInstance();
        return sessionsIDs.getCodecoolerId(sessionId);
    }

    private AdminDAOImpl getAdminDao() {
        DAOFactoryImpl daoFactory = new DAOFactoryImpl();
        return new AdminDAOImpl(daoFactory);
    }

    private void sendResponse(HttpExchange httpExchange, String response) throws IOException {
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
