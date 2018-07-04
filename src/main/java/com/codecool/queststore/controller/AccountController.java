package com.codecool.queststore.controller;

import com.codecool.queststore.DAO.AccountDAO;
import com.codecool.queststore.DAO.DAOFactory;
import com.codecool.queststore.DAO.DAOFactoryImpl;
import com.codecool.queststore.model.RequestFormater;
import com.codecool.queststore.model.SingletonAcountContainer;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpCookie;
import java.util.Map;
import java.util.UUID;

public class AccountController implements HttpHandler {

    private DAOFactory daoFactory;
    private AccountDAO accountDAO;

    public AccountController() {
        daoFactory = new DAOFactoryImpl();
        accountDAO = daoFactory.getAccountDAO();
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        String method = httpExchange.getRequestMethod();
        String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");

        if (pageLoadedAndCookieExists(method, cookieStr)) {
            redirectToMenuByCookie(cookieStr, httpExchange);
        } else if (userPushedLoginButton(method)) {
            redirectToMenuByUserType(httpExchange);
        } else {
            String response = getResponse();
            sendResponse(httpExchange, response);
        }
    }

    private boolean pageLoadedAndCookieExists(String method, String cookieStr) {
        return method.equals("GET") && cookieStr != null;
    }

    private boolean userPushedLoginButton(String method) {
        return method.equals("POST");
    }

    private void redirectToMenuByCookie(String cookieStr, HttpExchange httpExchange) throws IOException {

        String sessionId = getSessionIdbyCookie(cookieStr);
        if (sessionExpired(sessionId)) {
            String response = getResponse();
            sendResponse(httpExchange, response);
        } else {
            String accountType = getAccountType(sessionId);
            login(httpExchange, accountType);
        }
    }

    private String getSessionIdbyCookie(String cookieStr) {
        HttpCookie httpCookie = HttpCookie.parse(cookieStr).get(0);
        return httpCookie.toString().split("=")[1];
    }

    private boolean sessionExpired(String sessionId) {
        return sessionId == null;
    }

    private String getAccountType(String sessionId) {
        SingletonAcountContainer accountContainer = SingletonAcountContainer.getInstance();
        int codecoolerId = accountContainer.getCodecoolerId(sessionId);
        return accountDAO.getAccountType(codecoolerId);
    }

    private void redirectToMenuByUserType(HttpExchange httpExchange) throws IOException {

        Map<String, String> loginPassword = parseForm(httpExchange);
        String login = loginPassword.get("login");
        String password = loginPassword.get("password");

        if (loginPasswordValid(login, password)) {
            String uuid = UUID.randomUUID().toString();
            addNewSession(uuid, login, password);
            prepareCookieToSend(uuid, httpExchange);
            String accountType = accountDAO.getAccountType(login, password);
            login(httpExchange, accountType);
        } else {
            String response = getResponse("Login or password incorrect!");
            sendResponse(httpExchange, response);
        }
    }

    private Map<String, String> parseForm(HttpExchange httpExchange) throws IOException {
        RequestFormater requestFormater = new RequestFormater();
        return requestFormater.getMapFromRequest(httpExchange);
    }

    private boolean loginPasswordValid(String login, String password) {
        return accountDAO.validateAccount(login, password);
    }

    private void addNewSession(String uuid, String login, String password) {
        int id = accountDAO.getCodecoolerId(login, password);
        SingletonAcountContainer accountContainer = SingletonAcountContainer.getInstance();
        accountContainer.addAccount(uuid, id);
    }

    private void prepareCookieToSend(String uuid, HttpExchange httpExchange) {
        HttpCookie httpCookie = new HttpCookie("sessionId", uuid);
        httpExchange.getResponseHeaders().add("Set-Cookie", httpCookie.toString());
    }

    private void login(HttpExchange httpExchange, String accountType) throws IOException {
        if (accountType.equals("student")) {
            redirect(httpExchange, "student");
        } else if (accountType.equals("mentor")) {
            redirect(httpExchange, "mentor");
        } else if (accountType.equals("admin")) {
            redirect(httpExchange, "admin");
        }
    }

    private void redirect(HttpExchange httpExchange, String location) throws IOException {
        Headers headers = httpExchange.getResponseHeaders();
        headers.add("Location", location);
        httpExchange.sendResponseHeaders(302, -1);
        httpExchange.close();
    }

    private void sendResponse(HttpExchange httpExchange, String response) throws IOException {
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private String getResponse() {
        JtwigTemplate jtwigTemplate = JtwigTemplate.classpathTemplate("templates/index.twig");
        JtwigModel jtwigModel = JtwigModel.newModel();
        return jtwigTemplate.render(jtwigModel);
    }

    private String getResponse(String message) {
        JtwigTemplate jtwigTemplate = JtwigTemplate.classpathTemplate("templates/index.twig");
        JtwigModel jtwigModel = JtwigModel.newModel();
        jtwigModel.with("message", message);
        return jtwigTemplate.render(jtwigModel);
    }

}
