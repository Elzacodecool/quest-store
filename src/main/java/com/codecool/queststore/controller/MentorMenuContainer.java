package com.codecool.queststore.controller;

import com.codecool.queststore.DAO.*;
import com.codecool.queststore.model.RequestFormater;
import com.codecool.queststore.model.classRoom.ClassRoom;
import com.codecool.queststore.model.inventory.Category;
import com.codecool.queststore.model.inventory.Item;
import com.codecool.queststore.model.user.Mentor;
import com.codecool.queststore.model.user.Student;
import com.codecool.queststore.model.user.UserDetails;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.util.ArrayList;
import java.util.List;

public class MentorMenuContainer {

    private DAOFactory daoFactory;
    private AccountDAO accountDAO;
    private MentorDAO mentorDAO;
    private Mentor mentor;
    private RequestFormater requestFormater;
    private ClassDAO classDAO;
    private StudentDAO studentDAO;
    private ItemDAO itemDAO;
    private int questId;

    public MentorMenuContainer() {
        daoFactory = new DAOFactoryImpl();
        accountDAO = daoFactory.getAccountDAO();
        mentorDAO = daoFactory.getMentorDAO();
        requestFormater = new RequestFormater();
        classDAO = daoFactory.getClassDAO();
        studentDAO = daoFactory.getStudentDAO();
        itemDAO = daoFactory.getItemDAO();
    }


    public String getMenuMentor() {
        UserDetails userDetails = mentor.getUserDetails();
        String fullName = userDetails.getFirstName() + " " + userDetails.getLastName();
        JtwigTemplate jtwigTemplate = JtwigTemplate.classpathTemplate("templates/menu-mentor.twig");
        JtwigModel jtwigModel = JtwigModel.newModel();
        jtwigModel.with("fullname", fullName);
        String response = jtwigTemplate.render(jtwigModel);

        return response;
    }

    public String getMenuAddStudent() {
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

    public String getMenuAddArtifact() {
        UserDetails userDetails = mentor.getUserDetails();
        String fullName = userDetails.getFirstName() + " " + userDetails.getLastName();
        JtwigTemplate jtwigTemplate = JtwigTemplate.classpathTemplate("templates/add-artifact.twig");
        JtwigModel jtwigModel = JtwigModel.newModel();
        jtwigModel.with("fullname", fullName);
        String response = jtwigTemplate.render(jtwigModel);

        return response;
    }

    public String getMenuEditStudent(String login) {
        List<ClassRoom> classRoomList = classDAO.getAll();
        UserDetails userDetails = mentor.getUserDetails();
        String fullName = userDetails.getFirstName() + " " + userDetails.getLastName();
        Student student = studentDAO.getStudentByLogin(login);
        UserDetails userDetailsStudent = student.getUserDetails();
        JtwigTemplate jtwigTemplate = JtwigTemplate.classpathTemplate("templates/edit-student.twig");
        JtwigModel jtwigModel = JtwigModel.newModel();
        jtwigModel.with("fullname", fullName);
        jtwigModel.with("firstname", userDetailsStudent.getFirstName());
        jtwigModel.with("lastname", userDetailsStudent.getLastName());
        jtwigModel.with("login", userDetailsStudent.getLogin());
        jtwigModel.with("email", userDetailsStudent.getEmail());
        jtwigModel.with("classroom", classRoomList);
        String response = jtwigTemplate.render(jtwigModel);

        return response;
    }

    public String getMenuStudentClassesToEdit() {
        return getMenuClasses("templates/classes-edit-student.twig");
    }

    public String getMenuStudentClassesToAddQuest() {
        return getMenuClasses("templates/classes-quest-student.twig");
    }

    private String getMenuClasses(String templatePath) {
        List<ClassRoom> classRoomList = classDAO.getAll();
        UserDetails userDetails = mentor.getUserDetails();
        String fullName = userDetails.getFirstName() + " " + userDetails.getLastName();
        JtwigTemplate jtwigTemplate = JtwigTemplate.classpathTemplate(templatePath);
        JtwigModel jtwigModel = JtwigModel.newModel();
        jtwigModel.with("fullname", fullName);
        jtwigModel.with("classroom", classRoomList);
        String response = jtwigTemplate.render(jtwigModel);

        return response;
    }

    public String getMenuStudentOfClassToEdit(int idClass) {
        ClassRoom classRoom = classDAO.getClass(idClass);
        String className = classRoom.getClassName();
        List<UserDetails> userDetailsList = getUserDetailsList(idClass);
        JtwigTemplate jtwigTemplate = JtwigTemplate.classpathTemplate("templates/student-of-class-edit.twig");
        JtwigModel jtwigModel = JtwigModel.newModel();
        jtwigModel.with("classname", className);
        jtwigModel.with("users", userDetailsList);
        String response = jtwigTemplate.render(jtwigModel);

        return response;
    }

    public String getMenuArtifactsToEdit() {
        List<Item> artifactList = itemDAO.getArtifact();

        JtwigTemplate jtwigTemplate = JtwigTemplate.classpathTemplate("templates/edit-artifact-list.twig");
        JtwigModel jtwigModel = JtwigModel.newModel();
        List<Item> singleArtifactList = getArtifactByCategory(artifactList, "Single Artifact");
        System.out.println("size"+singleArtifactList.size());
        jtwigModel.with("singleartifact", singleArtifactList);
        List<Item> groupArtifactList = getArtifactByCategory(artifactList, "Group Artifact");
        jtwigModel.with("groupartifact", groupArtifactList);
        String response = jtwigTemplate.render(jtwigModel);

        return response;
    }

    private List<Item> getArtifactByCategory(List<Item> itemList, String category) {
        List<Item> artifactList = new ArrayList<>();
        for (Item item : itemList) {
            String itemCategoryName = item.getCategory().getName();
            if (category.equals(itemCategoryName)) {
                artifactList.add(item);
            }
        }

        return artifactList;
    }

    public String getMenuAddQuest() {
        UserDetails userDetails = mentor.getUserDetails();
        String fullName = userDetails.getFirstName() + " " + userDetails.getLastName();
        JtwigTemplate jtwigTemplate = JtwigTemplate.classpathTemplate("templates/add-quest.twig");
        JtwigModel jtwigModel = JtwigModel.newModel();
        jtwigModel.with("fullname", fullName);
        String response = jtwigTemplate.render(jtwigModel);

        return response;
    }

    public String getMenuQuestsStudents() {
        List<Item> questList = itemDAO.getQuests();
        UserDetails userDetails = mentor.getUserDetails();
        String fullName = userDetails.getFirstName() + " " + userDetails.getLastName();
        JtwigTemplate jtwigTemplate = JtwigTemplate.classpathTemplate("templates/quest-list-to-manage.twig");
        JtwigModel jtwigModel = JtwigModel.newModel();
        jtwigModel.with("fullname", fullName);
        jtwigModel.with("questlist", questList);
        String response = jtwigTemplate.render(jtwigModel);

        return response;
    }

    public String getMenuStudentChooser(int classId) {
        ClassRoom classRoom = classDAO.getClass(classId);
        String className = classRoom.getClassName();
        List<UserDetails> userDetailsList = getUserDetailsList(classId);
        System.out.println("size " + userDetailsList.size());
        JtwigTemplate jtwigTemplate = JtwigTemplate.classpathTemplate("templates/quest-to-students.twig");
        JtwigModel jtwigModel = JtwigModel.newModel();
        jtwigModel.with("classname", className);
        jtwigModel.with("users", userDetailsList);
        String response = jtwigTemplate.render(jtwigModel);

        return response;
    }

    private List getUserDetailsList(int idClass) {
        ClassRoom classRoom = classDAO.getClass(idClass);
        String className = classRoom.getClassName();
        List<Student> studentList = studentDAO.getStudentsByRoom(classRoom);
        List<UserDetails> userDetailsList = new ArrayList<>();
        for (Student student : studentList) {
            userDetailsList.add(student.getUserDetails());
        }

        return userDetailsList;
    }

    public void setMentor(Mentor mentor) {
        this.mentor = mentor;
    }
}
