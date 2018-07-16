package com.codecool.queststore.DAO;

import com.codecool.queststore.model.Transaction;
import com.codecool.queststore.model.classRoom.ClassRoom;
import com.codecool.queststore.model.inventory.Inventory;
import com.codecool.queststore.model.user.Student;
import com.codecool.queststore.model.user.UserDetails;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.Mock;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

//@FixMethodOrder(MethodSorters.JVM)

@RunWith(MockitoJUnitRunner.class)
class StudentDAOImplTest {


    @Mock
    private DataSource dS;

    @Mock
    private Connection c;

    @Mock
    private PreparedStatement stmt;

    @Mock
    private ResultSet rS;

    private Student student;
    private UserDetails userDetails;
    private ClassRoom classRoom;
    private StudentDAO studentDAO;

    @Before
    public void setUpEnvironment() throws Exception {

        System.out.println(dS);
        assertNotNull(dS);
        when(c.prepareStatement(any(String.class))).thenReturn(stmt);
        when(dS.getConnection()).thenReturn(c);

        when(stmt.executeQuery()).thenReturn(rS);
        System.out.println(userDetails.getFirstName());
        /*
        when(rS.first()).thenReturn(true);

        when(rS.getInt(1)).thenReturn(1);

        when(rS.getString(2)).thenReturn(p.getFirstName());

        when(rS.getString(3)).thenReturn(p.getLastName());

        when(stmt.executeQuery()).thenReturn(rS);*/

    }

    @Test
    public void testAddStudent() {
        /*System.out.println(studentDAO.getStudent(1).getUserDetails().getFirstName());
        studentDAO.add(student);
        System.out.println(studentDAO.getStudent(1).getUserDetails().getFirstName());*/
        classRoom = new ClassRoom(1, "webRoom");
        userDetails = new UserDetails("Szymon", "Słowik", "email@email.com",
                "login123", "password123", "student");
        student = new Student(1, userDetails, classRoom, new Inventory(1), new ArrayList<Transaction>());
        studentDAO = new StudentDAOImpl(new DAOFactoryImpl(new PGSimpleDataSource()));
        //studentDAO.add(student);
        assertEquals(studentDAO.getStudent(1).getUserDetails().getFirstName(), "Szymon");

    }

    private void setAddStudentEnvironment() throws Exception {
        stmt.setString(1, userDetails.getFirstName());
        stmt.setString(1, userDetails.getFirstName());
        stmt.setString(1, userDetails.getFirstName());
        stmt.setString(1, userDetails.getFirstName());
        stmt.setString(1, userDetails.getFirstName());

    }
    /*private StudentDAO studentDAO = new StudentDAOImpl(new DAOFactoryImpl());
    private Student expectedStudent = createExpectedStudent();
    private Integer expectedStudentID = 2;

    private Student createExpectedStudent() {
        UserDetails userDetails = new UserDetails("John", "Epic", "email@email.com",
                "expectedLogin", "expectedPassword", "Student");
        Student expectedStudent = new Student(userDetails, new ClassRoom(1, "webRoom"));
        return expectedStudent;
    }


    @Test
    public void testAddStudent() { ;
        assertEquals(expectedStudentID, studentDAO.add(expectedStudent));
    }


    @Test
    public void testBStudentDetails() {
        Student resultStudent = studentDAO.getStudent(expectedStudentID);
        String[] expectedStudentDetails = {Integer.toString(expectedStudentID), expectedStudent.getClassRoom().getClassName(),
                                            Integer.toString(expectedStudent.getClassRoom().getId())};

        String[] actualStudentDetails = {Integer.toString(resultStudent.getId()),
                resultStudent.getClassRoom().getClassName(),
                Integer.toString(resultStudent.getClassRoom().getId())};

        assertEquals(Arrays.asList(expectedStudentDetails), Arrays.asList(actualStudentDetails));
    }


    @Test
    public void testCUpdateStudent() {
        String[] expectedResult = getUpdateExpectedResult();
        studentDAO.update(expectedStudent);
        String[] actualResult = getUpdateActualResult();
        assertEquals(Arrays.asList(expectedResult), Arrays.asList(actualResult));
    }


    private String[] getUpdateExpectedResult() {
        ClassRoom classRoom = new ClassRoom(2, "testClass");
        String[] expectedResult = {Integer.toString(classRoom.getId()), classRoom.getClassName()};
        expectedStudent.setClassRoom(classRoom);
        return expectedResult;
    }


    @Test
    public void testRemoveStudent() {
        studentDAO.remove(expectedStudentID);
        Student actualStudent = studentDAO.getStudent(expectedStudentID);
        assertNull(actualStudent);
    }


    private String[] getUpdateActualResult() {
        Student actualStudent = studentDAO.getStudent(expectedStudentID);
        ClassRoom classRoom = actualStudent.getClassRoom();
        return new String[]{Integer.toString(classRoom.getId()), classRoom.getClassName()};
    }*/
}