package com.codecool.queststore.controller;

import com.codecool.queststore.DAO.AdminDAO;
import com.codecool.queststore.DAO.AdminDAOImpl;
import com.codecool.queststore.DAO.DAOFactory;
import com.codecool.queststore.DAO.DAOFactoryImpl;
import com.codecool.queststore.model.SingletonAcountContainer;
import com.codecool.queststore.model.user.Admin;
import com.codecool.queststore.model.user.UserDetails;
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
        String method = httpExchange.getRequestMethod();

        String response = "";
        if (requestToMenu(actionsDatas, method)) {
            response = getResponse("templates/menu-admin.twig");
        } else if (requestToAddMentor(actionsDatas, method)) {
            response = getResponse("templates/add-mentor.twig");
        } else if (mentorDataConfirmed(actionsDatas, method)) {
//            createMentor(httpExchange);
            redirect(httpExchange, "/admin");
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

    private boolean requestToMenu(Map<String, String> actionsDatas, String method) {
        boolean isDataCorrect = actionsDatas.get("data").equals("admin");
        boolean isGetMethod = method.equals("GET");
        return isDataCorrect && isGetMethod;
    }

    private boolean requestToAddMentor(Map<String, String> actionsDatas, String method) {
        boolean isActionCorrect = actionsDatas.get("action").contains("admin");
        boolean isDataCorrect = actionsDatas.get("data").equals("add-mentor");
        boolean isGetMethod = method.equals("GET");
        return isActionCorrect && isDataCorrect && isGetMethod;
    }

    private boolean mentorDataConfirmed(Map<String, String> actionsDatas, String method) {
        boolean isActionCorrect = actionsDatas.get("action").contains("admin");
        boolean isDataCorrect = actionsDatas.get("data").equals("add-mentor");
        boolean isPostMethod = method.equals("POST");
        return isActionCorrect && isDataCorrect && isPostMethod;
    }

    private String getResponse(String templatePath) {

        JtwigTemplate jtwigTemplate = JtwigTemplate.classpathTemplate(templatePath);
        JtwigModel jtwigModel = JtwigModel.newModel();
        setHeaderDetails(jtwigModel);

        return jtwigTemplate.render(jtwigModel);
    }

    private void setHeaderDetails(JtwigModel jtwigModel) {
        UserDetails userDetails = admin.getUserDetails();
        jtwigModel.with("fullname", userDetails.getFirstName() + " " + userDetails.getLastName());
    }

    private void sendResponse(HttpExchange httpExchange, String response) throws IOException {
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
