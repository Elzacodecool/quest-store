package com.codecool.queststore.controller;

import com.codecool.queststore.DAO.AdminDAO;
import com.codecool.queststore.DAO.AdminDAOImpl;
import com.codecool.queststore.DAO.DAOFactory;
import com.codecool.queststore.DAO.DAOFactoryImpl;
import com.codecool.queststore.model.SingletonAcountContainer;
import com.codecool.queststore.model.user.Admin;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpCookie;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class AdminController implements HttpHandler {

    private Admin admin;

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        redirectToLoginPageIfSessionExpired(httpExchange);

        this.admin = getAdminByCookie(httpExchange);
        Map<String, String> actionsDatas = parseURI(httpExchange);

        String response = "";
        if (requestToMenu(actionsDatas)) {
            response = getResponse(httpExchange, "templates/menu-admin.twig");
        }

        sendResponse(httpExchange, response);
    }

    private void redirectToLoginPageIfSessionExpired(HttpExchange httpExchange) throws IOException {
        String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");
        String sessionId = getSessionIdbyCookie(cookieStr);

        if (sessionExpired(sessionId)) { redirect(httpExchange, "index"); }
    }

    private String getSessionIdbyCookie(String cookieStr) {
        HttpCookie httpCookie = HttpCookie.parse(cookieStr).get(0);
        return httpCookie.toString().split("=")[1];
    }

    private boolean sessionExpired(String sessionId) {
        return sessionId == null;
    }

    private void redirect(HttpExchange httpExchange, String location) throws IOException {
        Headers headers = httpExchange.getResponseHeaders();
        headers.add("Location", location);
        httpExchange.sendResponseHeaders(302, -1);
        httpExchange.close();
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

    private Map<String, String> parseURI(HttpExchange httpExchange) {

        String uri = httpExchange.getRequestURI().toString();
        String[] actionsDatas = uri.split("/");

        Map <String, String> keyValue = new HashMap<>();
        for (int i = 0; i < actionsDatas.length - 1; i++) { keyValue.put("action", actionsDatas[i]); }
        keyValue.put("data", actionsDatas[actionsDatas.length - 1]);

        return keyValue;
    }

    private boolean requestToMenu(Map<String, String> actionsDatas) { return actionsDatas.get("data").equals("admin"); }

    private String getResponse(HttpExchange httpExchange, String templatePath) {

        Admin admin = getAdminByCookie(httpExchange);
        JtwigTemplate jtwigTemplate = JtwigTemplate.classpathTemplate(templatePath);
        JtwigModel jtwigModel = JtwigModel.newModel();
        jtwigModel.with("fullname", admin.getUserDetails().getFirstName() + " " + admin.getUserDetails().getLastName());
        String response = jtwigTemplate.render(jtwigModel);

        return response;
    }

    private void sendResponse(HttpExchange httpExchange, String response) throws IOException {
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
