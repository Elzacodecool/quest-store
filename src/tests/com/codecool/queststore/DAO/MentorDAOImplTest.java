package com.codecool.queststore.DAO;

import com.codecool.queststore.model.classRoom.ClassRoom;
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
import java.util.ArrayList;
import java.util.List;

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

    private void assertIfMentorsTheSame(Mentor mentor, Mentor mentor2) {
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

    private void setUpForReturnMentorList() throws Exception {
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
        when(resultSet.getInt(any(String.class))).thenReturn(1).thenReturn(1).thenReturn(2).thenReturn(2).thenReturn(3).thenReturn(3).thenReturn(4).thenReturn(4);
        UserDetails ud1 = new UserDetails("E", "K", "E", "K", "R", "S");
        UserDetails ud2 = new UserDetails("M", "R", "Z", "A", "B", "C");
        UserDetails ud3 = new UserDetails("S", "L", "E", "K", "R", "S");
        UserDetails ud4 = new UserDetails("K", "O", "Z", "A", "B", "C");

        when(userDAO.getUser(any(Integer.class))).thenReturn(ud1).thenReturn(ud2).thenReturn(ud3).thenReturn(ud4);
    }

    @Test
    public void shouldReturnMentorList_4Items() throws Exception {
        setUpForReturnMentorList();
        assertEquals(4, new MentorDAOImpl(daoFactory).getAllMentors().size());
    }

    private List<Mentor> createMentorsList() {
        List<Mentor> expectedMentors = new ArrayList<>();
        expectedMentors.add(new Mentor(1, new UserDetails("E", "K", "E", "K", "R", "S")));
        expectedMentors.add(new Mentor(2, new UserDetails("M", "R", "Z", "A", "B", "C")));
        expectedMentors.add(new Mentor(3, new UserDetails("S", "L", "E", "K", "R", "S")));
        expectedMentors.add(new Mentor(4, new UserDetails("K", "O", "Z", "A", "B", "C")));

        return expectedMentors;
    }

    @Test
    public void shouldReturnTheSameMentors() throws Exception {
        setUpForReturnMentorList();
        List<Mentor> mentors = new MentorDAOImpl(daoFactory).getAllMentors();
        List<Mentor> expectedMentors = createMentorsList();
        for (int i = 0; i < mentors.size(); i++) {
            assertIfMentorsTheSame(mentors.get(i), expectedMentors.get(i));
        }
    }

    @Test
    public void shouldReturnMentorsFromClassRoom_4items() throws Exception {
        ClassRoom classRoom = new ClassRoom(1, "python");
        setUpForReturnMentorList();
        assertEquals(4, new MentorDAOImpl(daoFactory).getMentorsFromClass(classRoom).size());
    }

    @Test
    public void shouldReturnEmptyListWhenException() throws Exception {
        ClassRoom classRoom = new ClassRoom(1, "python");
        setUpForNull();
        assertEquals(new MentorDAOImpl(daoFactory).getMentorsFromClass(classRoom).size(), 0);
    }
}