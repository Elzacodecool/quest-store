package com.codecool.queststore.DAO;

import com.codecool.queststore.model.Transaction;
import com.codecool.queststore.model.classRoom.ClassRoom;
import com.codecool.queststore.model.inventory.Inventory;
import com.codecool.queststore.model.user.Student;
import com.codecool.queststore.model.user.UserDetails;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.Mock;

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

    private Student student;
    private UserDetails userDetails;
    private ClassRoom classRoom;
    private StudentDAO studentDAO;

    public void setUpEnvironment() throws Exception {
        MockitoAnnotations.initMocks(this);
        initializePrivateFields();
        when(rS.next()).thenReturn(true);
        when(rS.getInt(1)).thenReturn(1);
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
        setUpEnvironment();
        when(userDAO.add(any(UserDetails.class))).thenReturn(1);
        assertEquals(Integer.valueOf(1), studentDAO.add(student));
    }

    @Test
    public void shouldAddStudentThrowsNullPointerException() throws Exception {
        Integer nullNumber = null;
        setUpEnvironment();
        when(rS.next()).thenReturn(false);
        when(rS.getInt(1)).thenReturn(nullNumber);
        //studentDAO.add(student);
        assertThrows(NullPointerException.class, () -> { studentDAO.add(student); });
    }

    private Integer teszt() {
        return null;
    }
}