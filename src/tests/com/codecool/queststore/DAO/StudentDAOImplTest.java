package com.codecool.queststore.DAO;

import com.codecool.queststore.model.Transaction;
import com.codecool.queststore.model.classRoom.ClassRoom;
import com.codecool.queststore.model.inventory.Inventory;
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
import java.util.ArrayList;


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
        student = new Student(1, userDetails, classRoom, new Inventory(1), new ArrayList<Transaction>());
        studentDAO = new StudentDAOImpl(daoFactory);
    }

    @Test
    public void shouldAddStudent() throws Exception {
        assertEquals(Integer.valueOf(1), studentDAO.add(student));
    }

    @Test
    public void shouldAddStudentThrowsNullPointerExceptionIfNullPassed() throws Exception {
        assertThrows(NullPointerException.class, () -> { studentDAO.add(null); });
    }

    @Test
    public void shouldGetStudent() {
        assertNotNull(studentDAO.getStudent(1));
    }
}