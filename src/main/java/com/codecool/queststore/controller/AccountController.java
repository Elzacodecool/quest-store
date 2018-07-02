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
        String response = getResponse();
        String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");
        HttpCookie httpCookie;
        String sessionId = "";
        SingletonAcountContainer accountContainer = SingletonAcountContainer.getInstance();

        if (method.equals("GET") && cookieStr != null) {
            httpCookie = HttpCookie.parse(cookieStr).get(0);
            sessionId = httpCookie.getValue();
            int id = 0;
            if (accountContainer.checkIfContains(sessionId)) {
                id = accountContainer.getCodecoolerId(sessionId);
                String accountType = accountDAO.getAccountType(id);
                login(httpExchange, accountType);
            }

        }

        if (method.equals("POST")) {
            RequestFormater requestFormater = new RequestFormater();
            Map<String, String> formMap = requestFormater.getMapFromRequest(httpExchange);
            if (accountDAO.validateAccount(formMap.get("login"), formMap.get("password"))) {
                UUID uuid = UUID.randomUUID();
                httpCookie = new HttpCookie("sessionId", uuid.toString());
                httpExchange.getResponseHeaders().add("Set-Cookie", httpCookie.toString());
                String accountType = accountDAO.getAccountType(formMap.get("login"), formMap.get("password"));
                int id = accountDAO.getCodecoolerId(formMap.get("login"), formMap.get("password"));
                accountContainer.addAccount(uuid.toString(), id);
                login(httpExchange, accountType);
            }
        }

        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
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

    private String getResponse() {
        JtwigTemplate jtwigTemplate = JtwigTemplate.classpathTemplate("templates/index.twig");
        JtwigModel jtwigModel = JtwigModel.newModel();
        String response = jtwigTemplate.render(jtwigModel);

        return response;
    }


}
