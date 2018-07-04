package com.codecool.queststore.controller;

import com.codecool.queststore.DAO.*;
import com.codecool.queststore.model.RequestFormater;
import com.codecool.queststore.model.SingletonAcountContainer;
import com.codecool.queststore.model.classRoom.ClassRoom;
import com.codecool.queststore.model.user.Mentor;
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
import java.net.URI;
import java.util.List;
import java.util.Map;

public class MentorController implements HttpHandler {

    private DAOFactory daoFactory;
    private AccountDAO accountDAO;
    private MentorDAO mentorDAO;
    private Mentor mentor;
    private RequestFormater requestFormater;
    private ClassDAO classDAO;
    private StudentDAO studentDAO;

    public MentorController() {
        daoFactory = new DAOFactoryImpl();
        accountDAO = daoFactory.getAccountDAO();
        mentorDAO = daoFactory.getMentorDAO();
        requestFormater = new RequestFormater();
        classDAO = daoFactory.getClassDAO();
        studentDAO = daoFactory.getStudentDAO();
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String method = httpExchange.getRequestMethod();
        URI uri = httpExchange.getRequestURI();
        String stringUri = uri.toString();
        System.out.println(stringUri);
        System.out.println(method);

        String response = "";
        String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");
        HttpCookie httpCookie;
        String sessionId = "";
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
                }
            }
        }
        response = getMenuMentor(mentor);

        if (stringUri.contains("add-student") && method.equals("GET")) {
            response = getAddStudent();

        } else if (stringUri.contains("add-student") && method.equals("POST")) {
            Map<String, String> formMap = requestFormater.getMapFromRequest(httpExchange);
            int classId = Integer.parseInt(formMap.get("room"));
            ClassRoom classRoom = classDAO.getClass(classId);
            UserDetails userDetails = new UserDetails(formMap.get("firstname"), formMap.get("lastname"), formMap.get("email"), formMap.get("login"), formMap.get("password"), "student");
            Student student = new Student(userDetails, classRoom);
            studentDAO.add(student);

        } else if (stringUri.equals("/mentor/student-classes") && method.equals("GET")) {
            response = getMenuStudentClasses();
        } else if (stringUri.contains("class-id") && method.equals("GET")) {
            int classId = getClassId(uri);
            response = getMenuStudentOfClass(classId);
        }


        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private String getMenuMentor(Mentor mentor) {
        UserDetails userDetails = mentor.getUserDetails();
        String fullName = userDetails.getFirstName() + " " + userDetails.getLastName();
        JtwigTemplate jtwigTemplate = JtwigTemplate.classpathTemplate("templates/menu-mentor.twig");
        JtwigModel jtwigModel = JtwigModel.newModel();
        jtwigModel.with("fullname", fullName);
        String response = jtwigTemplate.render(jtwigModel);

        return response;
    }

    private String getAddStudent() {
        List<ClassRoom> classRoomList = classDAO.getAll();
        UserDetails userDetails = mentor.getUserDetails();
        String fullName = userDetails.getFirstName() + " " + userDetails.getLastName();
        JtwigTemplate jtwigTemplate = JtwigTemplate.classpathTemplate("templates/add-student.twig");
        JtwigModel jtwigModel = JtwigModel.newModel();
        jtwigModel.with("fullname", fullName);
        jtwigModel.with("classroom", classRoomList);
        String response = jtwigTemplate.render(jtwigModel);

        return response;
    }

    private String getMenuStudentClasses() {
        List<ClassRoom> classRoomList = classDAO.getAll();
        UserDetails userDetails = mentor.getUserDetails();
        String fullName = userDetails.getFirstName() + " " + userDetails.getLastName();
        JtwigTemplate jtwigTemplate = JtwigTemplate.classpathTemplate("templates/classes.twig");
        JtwigModel jtwigModel = JtwigModel.newModel();
        jtwigModel.with("fullname", fullName);
        jtwigModel.with("classroom", classRoomList);
        String response = jtwigTemplate.render(jtwigModel);

        return response;
    }

    private String getMenuStudentOfClass(int idClass) {
        ClassRoom classRoom = classDAO.getClass(idClass);
        String className = classRoom.getClassName();
        List<Student> studentList = studentDAO.getStudentsByRoom(classRoom);
        UserDetails userDetails = mentor.getUserDetails();
        String fullName = userDetails.getFirstName() + " " + userDetails.getLastName();
        JtwigTemplate jtwigTemplate = JtwigTemplate.classpathTemplate("templates/classes.twig");
        JtwigModel jtwigModel = JtwigModel.newModel();
        jtwigModel.with("classname", className);
        jtwigModel.with("studentlist", studentList);
        String response = jtwigTemplate.render(jtwigModel);

        return response;
    }

    private void redirect(HttpExchange httpExchange, String location) throws IOException {
        Headers headers = httpExchange.getResponseHeaders();
        headers.add("Location", location);
        httpExchange.sendResponseHeaders(302, -1);
        httpExchange.close();
    }

    private int getClassId(URI uri) {
        String stringUri = uri.toString();
        String[] uriArray = stringUri.split("/");
        System.out.println(uriArray);
        int INDEX_CLASS_ID = uriArray.length - 1;
        String classId = uriArray[INDEX_CLASS_ID];

        return Integer.parseInt(classId);
    }
}
