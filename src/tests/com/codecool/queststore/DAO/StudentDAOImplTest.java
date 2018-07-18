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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
class StudentDAOImplTest {

    @Mock
    private ResultSet rS;

    @Mock
    private DAOFactoryImpl daoFactory;

    @Mock
    private UserDAO userDAO;

    @Mock
    private ClassDAO classDAO;

    @Mock
    private TransactionDAO transactionDAO;

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
        when(daoFactory.execQuery(any(String.class), any(Integer.class))).thenReturn(rS);

        when(daoFactory.execQuery(any(String.class),
                any(String.class),
                any(String.class),
                any(String.class),
                any(String.class),
                any(String.class),
                any(String.class))).thenReturn(rS);

        when(daoFactory.getUserDAO()).thenReturn(userDAO);
        when(userDAO.getUser(any(Integer.class))).thenReturn(userDetails);
        when(daoFactory.getClassDAO()).thenReturn(classDAO);
        when(classDAO.getClass(any(Integer.class))).thenReturn(classRoom);

        when(daoFactory.getTransactionDAO()).thenReturn(transactionDAO);
        when(transactionDAO.getTransactionByUser(any(Integer.class))).thenReturn(new ArrayList<Transaction>());

        when(userDAO.add(any(UserDetails.class))).thenReturn(1);


    }



    private void initializePrivateFields() {
        classRoom = new ClassRoom(1, "webRoom");
        userDetails = new UserDetails("Szymon", "Słowik", "email@email.com",
                "login123", "password123", "student");
        Category category = new Category("testItemCategory");
        Item item = new Item(1, "testItem", "testItemDescription", 100, category);
        inventory = new Inventory(1, Arrays.asList(new Item[]{item}));
        student = new Student(1, userDetails, classRoom, inventory, new ArrayList<>());
        studentDAO = new StudentDAOImpl(daoFactory);
    }

    @Test
    public void shouldAddStudent() throws Exception {
        assertEquals(Integer.valueOf(1), studentDAO.add(student));
    }

    @Test
    public void shouldAddStudentThrowExceptionIfNullPass() {
        assertThrows(NullPointerException.class, () -> { studentDAO.add(null); });
    }

    @Test
    public void shouldGetStudent() throws Exception {
        setupStudentInventory();
        assertNotNull(studentDAO.getStudent(1));
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
    public void shouldGetStudentHasProperUserDetails() throws Exception {
        when(rS.next()).thenReturn(false);

        String[] expectedUserDetails = {Integer.toString(userDetails.getId()),
                                        userDetails.getFirstName(),
                                        userDetails.getLastName(),
                                        userDetails.getEmail(),
                                        userDetails.getLogin(),
                                        userDetails.getPassword(),
                                        userDetails.getAccountType()};

        String[] actualUserDetails = getActualUserDetails();

        assertEquals(expectedUserDetails, actualUserDetails);
    }

    private String[] getActualUserDetails() {
        UserDetails actualUserDetails = studentDAO.getStudent(1).getUserDetails();

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
    public void shouldGetStudentReturnNullIfExceptionOccur() throws Exception {
        when(rS.getInt("class_id")).thenThrow(SQLException.class);
        assertNull(studentDAO.getStudent(1));
    }

    @Test
    public void shouldUpdateThrowExceptionIfNullPass() {
        assertThrows(NullPointerException.class, () -> { studentDAO.update(null);});
    }
}