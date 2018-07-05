package com.codecool.queststore.controller;

import com.codecool.queststore.DAO.*;
import com.codecool.queststore.model.SingletonAcountContainer;
import com.codecool.queststore.model.Transaction;
import com.codecool.queststore.model.classRoom.ClassRoom;
import com.codecool.queststore.model.inventory.Item;
import com.codecool.queststore.model.user.Student;
import com.codecool.queststore.model.user.UserDetails;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpCookie;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentController implements HttpHandler {

    private Student student;

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        redirectToLoginPageIfSessionExpired(httpExchange);

        this.student = getStudentByCookie(httpExchange);

        String method = httpExchange.getRequestMethod();
        String response = "";

        if (isGetMethod(method)) {
            response = constructResponse(httpExchange, response);
        } else {
            manageDataAndRedirect(httpExchange);
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

    private Student getStudentByCookie(HttpExchange httpExchange) {
        String sessionId = getSessionId(httpExchange);
        int codecoolerId = getCodecoolerId(sessionId);
        StudentDAOImpl studentDAO = getStudentDao();
        return studentDAO.getStudent(codecoolerId);
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

    private StudentDAOImpl getStudentDao() {
        DAOFactoryImpl daoFactory = new DAOFactoryImpl();
        return new StudentDAOImpl(daoFactory);
    }

    private String constructResponse(HttpExchange httpExchange, String response) throws IOException {
        String dataUri = getDataUri(httpExchange);

        switch (dataUri) {
            case "student":
                response = getResponse("templates/menu-student.twig");
                break;
            case "student-view":
                response = getResponse("templates/student-view-profile.twig");
                break;
            case "transactions-history":
                response = getResponse("templates/transactions-student-view.twig");
                break;
            case "store-artifacts-to-buy":
                response = getResponse("templates/store-student-buy-items.twig");
                break;
            case "buy":
                response = getResponse("templates/buy-artifact-student.twig", Integer.parseInt(getLastAction(httpExchange)));
                break;
            case "logout":
                clearSession();
                redirect(httpExchange, "/index");
                break;
        }

        return response;
    }

    private String getDataUri(HttpExchange httpExchange) {
        return parseURI(httpExchange).get("data");
    }

    private String getLastAction(HttpExchange httpExchange) { return parseURI(httpExchange).get("action");}

    private Map<String, String> parseURI(HttpExchange httpExchange) {

        String uri = httpExchange.getRequestURI().toString();
        String[] actionsDatas = uri.split("/");

        Map <String, String> keyValue = new HashMap<>();
        for (int i = 0; i < actionsDatas.length - 1; i++) { keyValue.put("action", actionsDatas[i]); }
        keyValue.put("data", actionsDatas[actionsDatas.length - 1]);

        return keyValue;
    }

    private boolean isGetMethod(String method) { return method.equals("GET"); }

    private void manageDataAndRedirect(HttpExchange httpExchange) throws IOException {
        String dataUri = getDataUri(httpExchange);

        switch (dataUri) {
            case "add-mentor":
                redirect(httpExchange, "/admin");
                break;
        }
    }

    private String getResponse(String templatePath) {

        JtwigTemplate jtwigTemplate = JtwigTemplate.classpathTemplate(templatePath);
        JtwigModel jtwigModel = JtwigModel.newModel();
        setHeaderDetails(jtwigModel);
        if (templatePath.contains("student-view-profile")) { setStudentInfo(jtwigModel); }
        if (templatePath.contains("transactions-student-view")) { setTransactionsInfo(jtwigModel); }
        if (templatePath.contains("store-student-buy-items.twig")) { setArtifactsInfo(jtwigModel); }
        return jtwigTemplate.render(jtwigModel);
    }

    private String getResponse(String templatePath, int itemId) {

        JtwigTemplate jtwigTemplate = JtwigTemplate.classpathTemplate(templatePath);
        JtwigModel jtwigModel = JtwigModel.newModel();
        setHeaderDetails(jtwigModel);
        setItemData(jtwigModel, itemId);
        return jtwigTemplate.render(jtwigModel);
    }

    private void setHeaderDetails(JtwigModel jtwigModel) {
        UserDetails userDetails = student.getUserDetails();
        jtwigModel.with("fullname", userDetails.getFirstName() + " " + userDetails.getLastName());
        jtwigModel.with("money", Integer.toString(student.getCash()));
    }

    private void setStudentInfo(JtwigModel jtwigModel) {
        UserDetails userDetails = student.getUserDetails();
        ClassRoom classRoom = student.getClassRoom();

        jtwigModel.with("firstname", userDetails.getFirstName());
        jtwigModel.with("lastname", userDetails.getLastName());
        jtwigModel.with("email", userDetails.getEmail());
        jtwigModel.with("login", userDetails.getLogin());
        jtwigModel.with("classname", classRoom.getClassName());
    }

    private void setTransactionsInfo(JtwigModel jtwigModel) {
        List<Transaction> transactions = student.getTransactionList();

        int totalAmount = 0;
        for (Transaction transaction : transactions) {
            totalAmount += transaction.getAmount();
        }

        jtwigModel.with("transactions", transactions);
        jtwigModel.with("totalamount", totalAmount);
    }

    private void setArtifactsInfo(JtwigModel jtwigModel) {
        DAOFactory daoFactory = new DAOFactoryImpl();
        ItemDAO itemDAO = daoFactory.getItemDAO();
        List<Item> artifacts = itemDAO.getArtifact();

        jtwigModel.with("artifacts", artifacts);
    }

    private void setItemData(JtwigModel jtwigModel, int itemId) {
        DAOFactory daoFactory = new DAOFactoryImpl();
        ItemDAO itemDAO = daoFactory.getItemDAO();
        Item artifact = itemDAO.get(itemId);

        jtwigModel.with("name", artifact.getName());
        jtwigModel.with("description", artifact.getDescription());
        jtwigModel.with("price", artifact.getPrice());
    }

    private void clearSession() {
        SingletonAcountContainer acountContainer = SingletonAcountContainer.getInstance();
        acountContainer.removeSession(student.getUserDetails().getId());
    }

    private void sendResponse(HttpExchange httpExchange, String response) throws IOException {
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
