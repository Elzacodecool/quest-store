package com.codecool.queststore.DAO;

import com.codecool.queststore.model.user.Admin;
import com.codecool.queststore.model.user.Mentor;
import com.codecool.queststore.model.user.UserDetails;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MentorDAOImplTest {

    @Mock
    private DAOFactory daoFactory;

    @Mock
    private UserDAO userDAO;

    @Mock
    private ResultSet resultSet;


    @Before
    public void setUp() {
        UserDetails ud = new UserDetails("FirstName", "LastName", "Email", "Login", "Pasword", "AccountType");
        when(daoFactory.execQuery(any(String.class), any(Integer.class))).thenReturn(resultSet);
        when(daoFactory.execQuery(any(String.class))).thenReturn(resultSet);
        when(userDAO.getUser(any(Integer.class))).thenReturn(ud);
        when(daoFactory.getUserDAO()).thenReturn(userDAO);
    }

    private void setUpForReturnMentor() throws Exception {
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(any(String.class))).thenReturn(1);
    }

    @Test
    public void shouldReturnMentor() throws Exception {
        UserDetails ud = new UserDetails("FirstName", "LastName", "Email", "Login", "Pasword", "AccountType");
        Mentor mentor1 = new Mentor(1, ud);
        setUpForReturnMentor();
        Mentor mentor2 = new MentorDAOImpl(daoFactory).getMentor(1);
        assertIfMentorsTheSame(mentor1, mentor2);
    }

    private void assertIfMentorsTheSame(Mentor mentor, Mentor mentor2 ) {
        assertEquals(mentor.getId(), mentor2.getId());
        assertEquals(mentor.getUserDetails().getFirstName(), mentor2.getUserDetails().getFirstName());
        assertEquals(mentor.getUserDetails().getLastName(), mentor2.getUserDetails().getLastName());
        assertEquals(mentor.getUserDetails().getLogin(), mentor2.getUserDetails().getLogin());
        assertEquals(mentor.getUserDetails().getPassword(), mentor2.getUserDetails().getPassword());
        assertEquals(mentor.getUserDetails().getEmail(), mentor2.getUserDetails().getEmail());
    }

    private void setUpForNull() throws Exception {
        when(resultSet.getInt(any(String.class))).thenThrow(new SQLException());
    }

    @Test
    public void shouldReturnNull() throws Exception {
        setUpForNull();
        assertEquals(new MentorDAOImpl(daoFactory).getMentor(1), null);
    }
}