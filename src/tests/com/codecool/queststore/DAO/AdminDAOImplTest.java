package com.codecool.queststore.DAO;

import com.codecool.queststore.model.user.Admin;
import com.codecool.queststore.model.user.UserDetails;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import java.sql.ResultSet;
import java.sql.SQLException;


@RunWith(MockitoJUnitRunner.class)
public class AdminDAOImplTest {

    @Mock
    private DAOFactory daoFactory;

    @Mock
    private UserDAO userDAO;

    @Mock
    private ResultSet rs;

    @Before
    public void setUp() throws Exception {
        UserDetails ud = new UserDetails("FirstName", "LastName", "Email", "Login", "Pasword", "AccountType");
        when(daoFactory.execQuery(any(String.class), any(Integer.class))).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        when(userDAO.getUser(any(Integer.class))).thenReturn(ud);
        when(daoFactory.getUserDAO()).thenReturn(userDAO);
    }

    private void setUpForReturnAdmin() throws Exception {
        when(rs.getInt(any(String.class))).thenReturn(1);
    }

    @Test
    public void shouldReturnAdmin() throws Exception {
        UserDetails ud = new UserDetails("FirstName", "LastName", "Email", "Login", "Pasword", "AccountType");
        Admin admin = new Admin(1, ud);
        setUpForReturnAdmin();
        Admin adminFromMethod = new AdminDAOImpl(daoFactory).getAdmin(1);
        assertEquals(admin.getId(), adminFromMethod.getId());
        assertEquals(admin.getUserDetails().getFirstName(), adminFromMethod.getUserDetails().getFirstName());
        assertEquals(admin.getUserDetails().getLastName(), adminFromMethod.getUserDetails().getLastName());
        assertEquals(admin.getUserDetails().getLogin(), adminFromMethod.getUserDetails().getLogin());
        assertEquals(admin.getUserDetails().getPassword(), adminFromMethod.getUserDetails().getPassword());
        assertEquals(admin.getUserDetails().getEmail(), adminFromMethod.getUserDetails().getEmail());
    }

    private void setUpForNull() throws Exception {
        when(rs.getInt(any(String.class))).thenThrow(new SQLException());
    }

    @Test
    public void shouldReturnNull() throws Exception {
        setUpForNull();
        assertEquals(new AdminDAOImpl(daoFactory).getAdmin(1), null);
    }
}