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

import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.reset;
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

    @Mock
    PreparedStatement preparedStatement;

    @Mock
    private Student s;

    private Student student;
    private UserDetails userDetails;
    private ClassRoom classRoom;
    private Inventory inventory;
    private StudentDAOImpl studentDAO;

    @BeforeEach
    public void setUpEnvironment() throws Exception {
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

        when(preparedStatement.executeQuery()).thenReturn(rS);
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
        classRoom = new ClassRoom(1, "webRoom");
        userDetails = new UserDetails(1, "Szymon", "Słowik", "email@email.com",
                "login123", "password123", "student");
        Category category = new Category("testItemCategory");
        Item item = new Item(1, "testItem", "testItemDescription", 100, category);
        inventory = new Inventory(1, Arrays.asList(new Item[]{item}));
        student = new Student(1, userDetails, classRoom, inventory, new ArrayList<>());
        studentDAO = new StudentDAOImpl(daoFactory);
    }


    @Test
    public void shouldAddStudent() {
        assertEquals(Integer.valueOf(1), studentDAO.add(student));
    }


    @Test
    public void shouldAddStudentThrowExceptionWhenNullPass() {
        assertThrows(IllegalArgumentException.class, () -> { studentDAO.add(null); });
    }


    @Test
    public void shouldGetStudent() throws Exception {
        when(rS.next()).thenReturn(false);
        assertNotNull(studentDAO.getStudent(1));
    }


    @Test
    public void shouldGetStudentHasProperUserDetails() throws Exception {
        when(rS.next()).thenReturn(false);

        String[] expectedUserDetails = {Integer.toString(userDetails.getId()),
                                        userDetails.getFirstName(),
                                        userDetails.getLastName(),
                                        userDetails.getEmail(),
                                        userDetails.getLogin(),
                                        userDetails.getPassword(),
                                        userDetails.getAccountType()};

        String[] actualUserDetails = getActualUserDetails(false);

        assertEquals(Arrays.asList(expectedUserDetails), Arrays.asList(actualUserDetails));
    }


    private String[] getActualUserDetails(boolean isByLogin) {
        UserDetails actualUserDetails;
        if (isByLogin) actualUserDetails = studentDAO.getStudentByLogin("login123").getUserDetails();
        else actualUserDetails = studentDAO.getStudent(1).getUserDetails();

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
    public void shouldGetStudentHasProperlyAddItemToInventory() throws Exception {
        setupStudentInventory();
        Inventory actualStudentInventory = studentDAO.getStudent(1).getInventory();
        assertEquals(inventory.getItems().size(), actualStudentInventory.getItems().size());
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
    public void shouldGetStudentReturnNullWhenExceptionOccur() throws Exception {
        when(rS.getInt("class_id")).thenThrow(SQLException.class);
        assertNull(studentDAO.getStudent(1));
    }


    @Test
    public void shouldUpdateThrowExceptionWhenNullPass() {
        assertThrows(IllegalArgumentException.class, () -> { studentDAO.update(null);});
    }


    @Test
    public void shouldGetStudentByLogin() throws Exception {
        when(rS.next()).thenReturn(false);
        assertNotNull(studentDAO.getStudentByLogin("login123"));
    }


    @Test
    public void shouldGetStudentByLoginHasProperUserDetails() throws Exception {
        when(rS.next()).thenReturn(false);

        String[] expectedUserDetails = {Integer.toString(userDetails.getId()),
                userDetails.getFirstName(),
                userDetails.getLastName(),
                userDetails.getEmail(),
                userDetails.getLogin(),
                userDetails.getPassword(),
                userDetails.getAccountType()};

        String[] actualUserDetails = getActualUserDetails(true);

        assertEquals(Arrays.asList(expectedUserDetails), Arrays.asList(actualUserDetails));
    }


    @Test
    public void shouldGetStudentByLoginHasProperlyAddItemToInventory() throws Exception {
        setupStudentInventory();
        Inventory actualStudentInventory = studentDAO.getStudentByLogin("login123").getInventory();
        assertEquals(inventory.getItems().size(), actualStudentInventory.getItems().size());
    }


    @Test
    public void shouldGetStudentByLoginReturnNullWhenExceptionOccur() throws Exception {
        when(rS.getInt("class_id")).thenThrow(SQLException.class);
        assertNull(studentDAO.getStudentByLogin("login123"));
    }


    @Test
    public void shouldGetStudentByLoginThrowsExceptionWhenNullPass() throws Exception {
        when(rS.next()).thenReturn(false);
        assertThrows(IllegalArgumentException.class, () -> { studentDAO.getStudentByLogin(null);});
    }


    @Test
    public void shouldGetStudentsByRoom() throws Exception {
        when(rS.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        assertEquals(2, studentDAO.getStudentsByRoom(classRoom).size());
    }


    @Test
    public void shouldGetStudentsByRoomReturnEmptyListWhenExceptionOccur() throws SQLException {
        when(rS.next()).thenThrow(SQLException.class);
        assertEquals(0, studentDAO.getStudentsByRoom(classRoom).size());
    }


    @Test
    public void shouldGetStudentsByRoomThrowsExceptionWhenNullPass() {
        assertThrows(IllegalArgumentException.class, () -> {
            studentDAO.getStudentsByRoom(null);
        });
    }


    @Test
    public void shouldGetAllStudents() throws SQLException {
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

        assertEquals(3, studentDAO.getAllStudents().size());
    }


    @Test
    public void shouldGetAllStudentsReturnEmptyListWhenExceptionOccur() throws SQLException {
        when(daoFactory.execQuery(anyString())).thenReturn(rS);
        when(rS.next()).thenThrow(SQLException.class);
        assertEquals(0, studentDAO.getAllStudents());
    }
}