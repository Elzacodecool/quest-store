package com.codecool.queststore.DAO;

import com.codecool.queststore.model.Transaction;
import com.codecool.queststore.model.classRoom.ClassRoom;
import com.codecool.queststore.model.inventory.Category;
import com.codecool.queststore.model.inventory.Inventory;
import com.codecool.queststore.model.inventory.Item;
import com.codecool.queststore.model.user.Student;
import com.codecool.queststore.model.user.UserDetails;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.Mock;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyChar;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
class StudentDAOImplTest {

    @Mock
    private ResultSet rS;

    @Mock
    private DAOFactory daoFactory;

    @Mock
    private UserDAO userDAO;

    @Mock
    private ClassDAO classDAO;

    @Mock
    private TransactionDAO transactionDAO;

    private List<String> expectedUserDetails;

    private Student student;
    private UserDetails userDetails;
    private ClassRoom classRoom;
    private Inventory inventory;
    private StudentDAOImpl studentDAO;

    @BeforeEach
    void setUpEnvironment() throws Exception {
        MockitoAnnotations.initMocks(this);
        initializePrivateFields();
        when(rS.next()).thenReturn(true);
        when(rS.getInt("student_id")).thenReturn(1);
        when(rS.getInt("codecooler_id")).thenReturn(1);
        when(daoFactory.execQuery(any(String.class), any(Integer.class))).thenReturn(rS);
        when(daoFactory.execQuery(any(String.class), any(String.class))).thenReturn(rS);

        when(daoFactory.execQuery(any(String.class),
                any(String.class),
                any(String.class),
                any(String.class),
                any(String.class),
                any(String.class),
                any(String.class))).thenReturn(rS);

        when(daoFactory.getUserDAO()).thenReturn(userDAO);
        when(userDAO.getUser(any(Integer.class))).thenReturn(userDetails);
        when(userDAO.getUserByLogin(any(String.class))).thenReturn(userDetails);
        when(daoFactory.getClassDAO()).thenReturn(classDAO);
        when(classDAO.getClass(any(Integer.class))).thenReturn(classRoom);

        when(daoFactory.getTransactionDAO()).thenReturn(transactionDAO);
        when(transactionDAO.getTransactionByUser(any(Integer.class))).thenReturn(new ArrayList<Transaction>());

        when(userDAO.add(any(UserDetails.class))).thenReturn(1);
        when(daoFactory.getTransactionDAO().getTransactionByUser(any(Integer.class))).thenReturn(new ArrayList<>());


    }


    private void initializePrivateFields() {
        setInventory();
        setStudent();
        studentDAO = new StudentDAOImpl(daoFactory);
        expectedUserDetails  = Arrays.asList(Integer.toString(userDetails.getId()),
                userDetails.getFirstName(),
                userDetails.getLastName(),
                userDetails.getEmail(),
                userDetails.getLogin(),
                userDetails.getPassword(),
                userDetails.getAccountType());
    }


    private void setInventory() {
        Category category = new Category("testItemCategory");
        Item item = new Item(1, "testItem", "testItemDescription", 100, category);
        inventory = new Inventory(1, Arrays.asList(new Item[]{item}));
    }


    private void setStudent() {
        classRoom = new ClassRoom(1, "webRoom");
        userDetails = new UserDetails(1, "Szymon", "Słowik", "email@email.com",
                "login123", "password123", "student");
        student = new Student(1, userDetails, classRoom, inventory, new ArrayList<>());
    }


    @Test
    void shouldAddStudent() {
        Integer expectedID = 1;
        Integer actualID = studentDAO.add(student);

        assertEquals(expectedID, actualID);
    }


    @Test
    void shouldAddStudentThrowExceptionWhenNullPass() {
        assertThrows(IllegalArgumentException.class, () -> {
            studentDAO.add(null);
        });
    }


    @Test
    void shouldGetStudent() throws Exception {
        when(rS.next()).thenReturn(false);

        Student actualStudent = studentDAO.getStudent(1);

        assertNotNull(actualStudent);
    }


    @Test
    void shouldGetStudentHasProperUserDetails() throws Exception {
        when(rS.next()).thenReturn(false);

        List<String> actualUserDetails = Arrays.asList(getActualUserDetails(false));

        assertEquals(Arrays.asList(expectedUserDetails), Arrays.asList(actualUserDetails));
    }


    private String[] getActualUserDetails(boolean isByLogin) {
        UserDetails actualUserDetails;
        if (isByLogin)
            actualUserDetails = studentDAO.getStudentByLogin("login123").getUserDetails();
        else
            actualUserDetails = studentDAO.getStudent(1).getUserDetails();

        return new String[]{Integer.toString(actualUserDetails.getId()),
                            actualUserDetails.getFirstName(),
                            actualUserDetails.getLastName(),
                            actualUserDetails.getEmail(),
                            actualUserDetails.getLogin(),
                            actualUserDetails.getPassword(),
                            actualUserDetails.getAccountType()
        };
    }


    @Test
    void shouldGetStudentHasProperlyAddItemToInventory() throws Exception {
        setupStudentInventory();

        int actualStudentInventorySize = studentDAO.getStudent(1).
                                                    getInventory().
                                                    getItems().
                                                    size();

        int expectedStudentInventorySize = inventory.getItems().size();

        assertEquals(expectedStudentInventorySize, actualStudentInventorySize);
    }


    private void setupStudentInventory() throws Exception {
        when(rS.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(rS.getInt("item_id")).thenReturn(1);
        when(rS.getString("name")).thenReturn("testItem");
        when(rS.getString("decription")).thenReturn("testItemDescription");
        when(rS.getInt("price")).thenReturn(100);
        when(rS.getString("category")).thenReturn("testItemCategory");
    }


    @Test
    void shouldGetStudentReturnNullWhenExceptionOccur() throws Exception {
        when(rS.getInt("class_id")).thenThrow(SQLException.class);

        Student actualStudent = studentDAO.getStudent(1);

        assertNull(actualStudent);
    }


    @Test
    void shouldUpdateThrowExceptionWhenNullPass() {
        assertThrows(IllegalArgumentException.class, () -> {
            studentDAO.update(null);
        });
    }


    @Test
    void shouldGetStudentByLogin() throws Exception {
        when(rS.next()).thenReturn(false);

        Student actualStudent = studentDAO.getStudentByLogin("login123");

        assertNotNull(actualStudent);
    }


    @Test
    void shouldGetStudentByLoginHasProperUserDetails() throws Exception {
        when(rS.next()).thenReturn(false);

        List<String> actualUserDetails = Arrays.asList(getActualUserDetails(true));

        assertEquals(expectedUserDetails, actualUserDetails);
    }


    @Test
    void shouldGetStudentByLoginHasProperlyAddItemToInventory() throws Exception {
        setupStudentInventory();

        int actualStudentInventorySize = studentDAO.getStudentByLogin("login123").
                                                    getInventory().
                                                    getItems().
                                                    size();

        int expectedStudentInventorySize = 1;
        assertEquals(expectedStudentInventorySize, actualStudentInventorySize);
    }


    @Test
    void shouldGetStudentByLoginReturnNullWhenExceptionOccur() throws Exception {
        when(rS.getInt("class_id")).thenThrow(SQLException.class);

        assertNull(studentDAO.getStudentByLogin("login123"));
    }


    @Test
    void shouldGetStudentByLoginThrowsExceptionWhenNullPass() throws Exception {
        when(rS.next()).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> {
            studentDAO.getStudentByLogin(null);
        });
    }


    @Test
    void shouldGetStudentsByRoom() throws Exception {
        when(rS.next()).thenReturn(true).thenReturn(true).thenReturn(false);

        int expectedStudents = 2;
        int actualStudents = studentDAO.getStudentsByRoom(classRoom).size();

        assertEquals(expectedStudents, actualStudents);
    }


    @Test
    void shouldGetStudentsByRoomReturnEmptyListWhenExceptionOccur() throws SQLException {
        when(rS.next()).thenThrow(SQLException.class);

        int expectedStudents = 0;
        int actualStudents = studentDAO.getStudentsByRoom(classRoom).size();

        assertEquals(expectedStudents, actualStudents);
    }


    @Test
    void shouldGetStudentsByRoomThrowsExceptionWhenNullPass() {
        assertThrows(IllegalArgumentException.class, () -> {
            studentDAO.getStudentsByRoom(null);
        });
    }


    @Test
    void shouldGetAllStudents() throws SQLException {
        when(daoFactory.execQuery(anyString())).thenReturn(rS);
        when(rS.next()).thenReturn(true).
                thenReturn(true).
                thenReturn(true).
                thenReturn(false).
                thenReturn(true).
                thenReturn(true).
                thenReturn(true).
                thenReturn(false).
                thenReturn(true).
                thenReturn(true).
                thenReturn(true).
                thenReturn(false);

        int expectedStudents = 3;
        int actualStudents = studentDAO.getAllStudents().size();

        assertEquals(expectedStudents, actualStudents);
    }


    @Test
    void shouldGetAllStudentsReturnEmptyListWhenExceptionOccur() throws SQLException {
        when(daoFactory.execQuery(anyString())).thenReturn(rS);
        when(rS.next()).thenThrow(SQLException.class);

        int expectedStudents = 0;
        int actualStudents = studentDAO.getAllStudents().size();

        assertEquals(expectedStudents, actualStudents);
    }


    @Test
    void shouldRemoveThrowsExceptionWhenNullPass() {
        assertThrows(IllegalArgumentException.class, () -> {
            studentDAO.remove(passNullIntegerToMethod());
        });
    }


    private Integer passNullIntegerToMethod() {
        return null;
    }
}