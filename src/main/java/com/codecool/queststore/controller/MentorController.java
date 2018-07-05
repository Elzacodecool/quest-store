package com.codecool.queststore.controller;

import com.codecool.queststore.DAO.*;
import com.codecool.queststore.model.RequestFormater;
import com.codecool.queststore.model.SingletonAcountContainer;
import com.codecool.queststore.model.classRoom.ClassRoom;
import com.codecool.queststore.model.inventory.Category;
import com.codecool.queststore.model.inventory.Item;
import com.codecool.queststore.model.user.Mentor;
import com.codecool.queststore.model.user.Student;
import com.codecool.queststore.model.user.UserDetails;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpCookie;
import java.net.URI;
import java.util.Map;

public class MentorController implements HttpHandler {

    private DAOFactory daoFactory;
    private AccountDAO accountDAO;
    private MentorDAO mentorDAO;
    private Mentor mentor;
    private RequestFormater requestFormater;
    private ClassDAO classDAO;
    private StudentDAO studentDAO;
    private ItemDAO itemDAO;
    private MentorMenuContainer mentorMenuContainer;
    private QuestManagment questManagment;
    private String response;
    private String sessionId;

    public MentorController() {
        daoFactory = new DAOFactoryImpl();
        accountDAO = daoFactory.getAccountDAO();
        mentorDAO = daoFactory.getMentorDAO();
        requestFormater = new RequestFormater();
        classDAO = daoFactory.getClassDAO();
        studentDAO = daoFactory.getStudentDAO();
        itemDAO = daoFactory.getItemDAO();
        mentorMenuContainer = new MentorMenuContainer();
        questManagment = new QuestManagment();
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String method = httpExchange.getRequestMethod();
        URI uri = httpExchange.getRequestURI();
        String stringUri = uri.toString();
        System.out.println(stringUri);
        System.out.println(method);


        String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");
        HttpCookie httpCookie;
        SingletonAcountContainer accountContainer = SingletonAcountContainer.getInstance();
        if (stringUri.contains("mentor") && cookieStr != null) {
            httpCookie = HttpCookie.parse(cookieStr).get(0);
            sessionId = httpCookie.getValue();
            int id = 0;
            if (accountContainer.checkIfContains(sessionId)) {
                id = accountContainer.getCodecoolerId(sessionId);
                String accountType = accountDAO.getAccountType(id);
                if (!accountType.equals("mentor")) {
                    redirect(httpExchange, "account");
                } else {
                    mentor = mentorDAO.getMentor(id);
                    mentorMenuContainer.setMentor(mentor);
                }
            }
        }
        response = mentorMenuContainer.getMenuMentor();

        if (stringUri.contains("add-student") && method.equals("GET")) {
            response = mentorMenuContainer.getMenuAddStudent();

        } else if (stringUri.contains("add-student") && method.equals("POST")) {
            Map<String, String> formMap = requestFormater.getMapFromRequest(httpExchange);
            int classId = Integer.parseInt(formMap.get("room"));
            ClassRoom classRoom = classDAO.getClass(classId);
            UserDetails userDetails = new UserDetails(formMap.get("firstname"), formMap.get("lastname"), formMap.get("email"), formMap.get("login"), formMap.get("password"), "student");
            Student student = new Student(userDetails, classRoom);
            studentDAO.add(student);

        } else if (stringUri.equals("/mentor/student-classes") && method.equals("GET")) {
            response = mentorMenuContainer.getMenuStudentClassesToEdit();

        } else if (stringUri.contains("/mentor/student-classes/class-id") && method.equals("GET")) {
            int classId = getParameter(uri);
            response = mentorMenuContainer.getMenuStudentOfClassToEdit(classId);

        } else if (stringUri.contains("student-to-edit") && method.equals("GET")) {
            String login = getLogin(uri);
            response = mentorMenuContainer.getMenuEditStudent(login);

        } else if (stringUri.contains("student-to-edit") && method.equals("POST")) {
            Map<String, String> formMap = requestFormater.getMapFromRequest(httpExchange);
            int classId = Integer.parseInt(formMap.get("room"));
            ClassRoom classRoom = classDAO.getClass(classId);
            UserDetails userDetails = new UserDetails(formMap.get("firstname"), formMap.get("lastname"), formMap.get("email"), formMap.get("login"), formMap.get("password"), "student");
            Student student = new Student(userDetails, classRoom);
            studentDAO.update(student);
            redirect(httpExchange, "/mentor");

        } else if (stringUri.contains("add-artifact") && method.equals("GET")) {
            response = mentorMenuContainer.getMenuAddArtifact();

        } else if (stringUri.contains("add-artifact") && method.equals("POST")) {
            Map<String, String> formMap = requestFormater.getMapFromRequest(httpExchange);
            int price = Integer.parseInt(formMap.get("price"));
            Category category = new Category(formMap.get("category"));
            Item item = new Item(formMap.get("artifactname"), formMap.get("description"), price, category);
            itemDAO.add(item);
            redirect(httpExchange, "/mentor");

        } else if (stringUri.contains("add-quest") && method.equals("GET")) {
            response = mentorMenuContainer.getMenuAddQuest();

        } else if (stringUri.contains("add-quest") && method.equals("POST")) {
            Map<String, String> formMap = requestFormater.getMapFromRequest(httpExchange);
            int price = -Integer.parseInt(formMap.get("reward"));
            Category category = new Category("Quest");
            Item item = new Item(formMap.get("questname"), formMap.get("description"), price, category);
            itemDAO.add(item);
            redirect(httpExchange, "/mentor");

        } else if (stringUri.equals("/mentor/quests-students") && method.equals("GET")) {
            response = mentorMenuContainer.getMenuQuestsStudents();

        } else if (stringUri.contains("quest-id") && method.equals("GET")) {
            response = mentorMenuContainer.getMenuQuestsStudents();
            int questId = getParameter(uri);
            questManagment.setQuestId(questId);
            redirect(httpExchange, "/mentor/quest-classes");

        } else if (stringUri.contains("/mentor/quest-classes") && method.equals("GET")) {
            response = mentorMenuContainer.getMenuStudentClassesToAddQuest();

        } else if (stringUri.contains("quest-student-classes/class-id") && method.equals("GET")) {
            int classId = getParameter(uri);
            questManagment.setClassId(classId);
            redirect(httpExchange, "/mentor/quest-to-students");


        }  else if (stringUri.contains("/mentor/quest-to-students") && method.equals("GET")) {
            response = mentorMenuContainer.getMenuStudentChooser(questManagment.getClassId());

        } else if (stringUri.contains("/mentor/quest-to-students") && method.equals("POST")) {
            Map<String, String> formMap = requestFormater.getMapFromRequest(httpExchange);
            questManagment.addQuestToStudents(formMap);
            response = mentorMenuContainer.getMenuStudentChooser(questManagment.getClassId());
            redirect(httpExchange, "/mentor");
        }


        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private void redirect(HttpExchange httpExchange, String location) throws IOException {
        Headers headers = httpExchange.getResponseHeaders();
        headers.add("Location", location);
        httpExchange.sendResponseHeaders(302, -1);
        httpExchange.close();
    }

    private int getParameter(URI uri) {
        String stringUri = uri.toString();
        String[] uriArray = stringUri.split("/");
        System.out.println(uriArray);
        int INDEX_PARAMETER = uriArray.length - 1;
        String classId = uriArray[INDEX_PARAMETER];

        return Integer.parseInt(classId);
    }

    private String getLogin(URI uri) {
        String stringUri = uri.toString();
        String[] uriArray = stringUri.split("/");
        System.out.println(uriArray);
        int INDEX_LOGIN = uriArray.length - 1;

        return uriArray[INDEX_LOGIN];
    }
}
