package com.codecool.queststore;


import com.codecool.queststore.DAO.DAOFactory;
import com.codecool.queststore.DAO.DAOFactoryImpl;
import com.codecool.queststore.model.classRoom.ClassRoom;
import com.codecool.queststore.model.inventory.Item;
import com.codecool.queststore.model.user.Student;
import com.codecool.queststore.model.user.UserDetails;

public class App {
    public static void main(String[] args) {

        DAOFactory DAO = new DAOFactoryImpl();
        UserDetails ud = new UserDetails("Jon", "Doe", "jon@doe.com", "jondoe", "password");
        ClassRoom classroom = new ClassRoom(1, "webRoom");
        Student student = new Student(ud, classroom);
        Student dupa = DAO.getStudentDAO().getStudent(4);
        ClassRoom cr = DAO.getClassDAO().getClass(2);
        System.out.println(dupa.getUserDetails().getPassword());
        System.out.println(dupa.getClassRoom().getClassName());
        dupa.getUserDetails().setPassword("wooopwoop");
//        dupa.setClassRoom(classroom);
        dupa.setClassRoom(cr);
        DAO.getStudentDAO().update(dupa);
//        DAO.getStudentDAO().remove(3);

//        List<Student> studentList = DAO.getStudentDAO().ge
//        System.out.println(DAO.getStudentDAO().getStudent(4).getUserDetails().getFirstName());
//        System.out.println(DAO.getStudentDAO().getStudent(4).getUserDetails().getLastName());
        System.out.println(DAO.getStudentDAO().getStudent(4).getUserDetails().getPassword());
        System.out.println(DAO.getStudentDAO().getStudent(4).getClassRoom().getClassName());
//        System.out.println(DAO.getStudentDAO().getStudent(4).getUserDetails().getLogin());
//        System.out.println(DAO.getStudentDAO().getStudent(4).getUserDetails().getPassword());
//        System.out.println(DAO.getStudentDAO().getStudent(4).getUserDetails().getId());
//        System.out.println("Inventory:");
//        for(Item i : DAO.getStudentDAO().getStudent(1).getInventory().getItems()) {
//            System.out.println(i.getName());
//            System.out.println(i.getDescription());
//            System.out.println(i.getPrice());
//            System.out.println(i.getId());
//        }
    }
}
