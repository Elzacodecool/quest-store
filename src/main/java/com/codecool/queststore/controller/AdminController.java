package com.codecool.queststore.controller;

import com.codecool.queststore.DAO.*;
import com.codecool.queststore.model.RequestFormater;
import com.codecool.queststore.model.SingletonAcountContainer;
import com.codecool.queststore.model.classRoom.ClassRoom;
import com.codecool.queststore.model.user.Admin;
import com.codecool.queststore.model.user.Mentor;
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
import java.util.List;
import java.util.Map;

public class AdminController implements HttpHandler {

    private Admin admin;

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        redirectToLoginPageIfSessionExpired(httpExchange);

        this.admin = getAdminByCookie(httpExchange);

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

    private String constructResponse(HttpExchange httpExchange, String response) throws IOException {
        String dataUri = getDataUri(httpExchange);

        switch (dataUri) {
            case "admin":
                response = getResponse("templates/menu-admin.twig");
                break;
            case "add-mentor":
                response = getResponse("templates/add-mentor.twig");
                break;
            case "add-class":
                response = getResponse("templates/add-class.twig");
                break;
            case "list-classes":
                response = getResponse("templates/classes-list-admin-view.twig");
                break;
            case "edit-class":
                response = getResponse("templates/edit-class.twig", Integer.parseInt(getLastAction(httpExchange)));
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
                createMentor(httpExchange);
                redirect(httpExchange, "/admin");
                break;
            case "add-class":
                createClassRoom(httpExchange);
                redirect(httpExchange, "/admin");
                break;
            case "edit-class":
                updateClassRoom(httpExchange);
                redirect(httpExchange, "/admin");
        }
    }

    private void createMentor(HttpExchange httpExchange) throws IOException {
        Map<String, String> formMap = new RequestFormater().getMapFromRequest(httpExchange);
        UserDetails userDetails = new UserDetails(formMap.get("firstname"), formMap.get("lastname"), formMap.get("email"), formMap.get("login"), formMap.get("password"), "mentor");
        Mentor mentor = new Mentor(0, userDetails);
        DAOFactoryImpl daoFactory = new DAOFactoryImpl();
        MentorDAO mentorDAO = daoFactory.getMentorDAO();
        mentorDAO.add(mentor);
    }

    private void createClassRoom(HttpExchange httpExchange) throws IOException {
        Map<String, String> formMap = new RequestFormater().getMapFromRequest(httpExchange);
        String classRoomName = formMap.get("classname");
        ClassRoom classRoom = new ClassRoom(0, classRoomName);
        DAOFactoryImpl daoFactory = new DAOFactoryImpl();
        ClassDAO classDAO = daoFactory.getClassDAO();
        classDAO.add(classRoom);
    }

    private void updateClassRoom(HttpExchange httpExchange) throws IOException {
        Map<String, String> formMap = new RequestFormater().getMapFromRequest(httpExchange);
        String classRoomName = formMap.get("classname");

        int classId = Integer.parseInt(getLastAction(httpExchange));

        DAOFactoryImpl daoFactory = new DAOFactoryImpl();
        ClassDAO classDAO = daoFactory.getClassDAO();
        ClassRoom classRoom = classDAO.getClass(classId);

        classRoom.setClassName(classRoomName);
        classDAO.update(classRoom);// TODO - WHY DAO UPDATES ALL CLASSROOMS ???
    }

    private void clearSession() {
        SingletonAcountContainer acountContainer = SingletonAcountContainer.getInstance();
        acountContainer.removeSession(admin.getUserDetails().getId());
    }

    private String getResponse(String templatePath) {

        JtwigTemplate jtwigTemplate = JtwigTemplate.classpathTemplate(templatePath);
        JtwigModel jtwigModel = JtwigModel.newModel();
        setHeaderDetails(jtwigModel);
        if (templatePath.contains("add-mentor") || templatePath.contains("classes-list-admin-view")) { setClassRooms(jtwigModel); }

        return jtwigTemplate.render(jtwigModel);
    }

    private String getResponse(String templatePath, int classId) {

        JtwigTemplate jtwigTemplate = JtwigTemplate.classpathTemplate(templatePath);
        JtwigModel jtwigModel = JtwigModel.newModel();
        setHeaderDetails(jtwigModel);
        DAOFactoryImpl daoFactory = new DAOFactoryImpl();
        ClassDAO classDAO = daoFactory.getClassDAO();
        ClassRoom classRoom = classDAO.getClass(classId);
        jtwigModel.with("classname", classRoom.getClassName());

        return jtwigTemplate.render(jtwigModel);
    }

    private void setHeaderDetails(JtwigModel jtwigModel) {
        UserDetails userDetails = admin.getUserDetails();
        jtwigModel.with("fullname", userDetails.getFirstName() + " " + userDetails.getLastName());
    }

    private void setClassRooms(JtwigModel jtwigModel) {
        List<ClassRoom> classRooms = new DAOFactoryImpl().getClassDAO().getAll();
        jtwigModel.with("classroom", classRooms);
    }

    private void sendResponse(HttpExchange httpExchange, String response) throws IOException {
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
