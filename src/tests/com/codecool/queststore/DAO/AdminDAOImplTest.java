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
import java.util.ArrayList;
import java.util.List;


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
        when(daoFactory.execQuery(any(String.class))).thenReturn(rs);
        when(userDAO.getUser(any(Integer.class))).thenReturn(ud);
        when(daoFactory.getUserDAO()).thenReturn(userDAO);
    }

    private void setUpForReturnAdmin() throws Exception {
        when(rs.next()).thenReturn(true);
        when(rs.getInt(any(String.class))).thenReturn(1);
    }

    private void assertIfAdminsTheSame(Admin admin1, Admin admin2 ) {
        assertEquals(admin1.getId(), admin2.getId());
        assertEquals(admin1.getUserDetails().getFirstName(), admin2.getUserDetails().getFirstName());
        assertEquals(admin1.getUserDetails().getLastName(), admin2.getUserDetails().getLastName());
        assertEquals(admin1.getUserDetails().getLogin(), admin2.getUserDetails().getLogin());
        assertEquals(admin1.getUserDetails().getPassword(), admin2.getUserDetails().getPassword());
        assertEquals(admin1.getUserDetails().getEmail(), admin2.getUserDetails().getEmail());
    }

    @Test
    public void shouldReturnAdmin() throws Exception {
        UserDetails ud = new UserDetails("FirstName", "LastName", "Email", "Login", "Pasword", "AccountType");
        Admin admin1 = new Admin(1, ud);
        setUpForReturnAdmin();
        Admin admin2 = new AdminDAOImpl(daoFactory).getAdmin(1);
        assertIfAdminsTheSame(admin1, admin2);
    }

    private void setUpForNull() throws Exception {
        when(rs.getInt(any(String.class))).thenThrow(new SQLException());
    }

    @Test
    public void shouldReturnNull() throws Exception {
        setUpForNull();
        assertEquals(new AdminDAOImpl(daoFactory).getAdmin(1), null);
    }

    private void setUpForReturnAdminList() throws Exception {
        when(rs.next()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
        when(rs.getInt(any(String.class))).thenReturn(1).thenReturn(1).thenReturn(2).thenReturn(2).thenReturn(3).thenReturn(3).thenReturn(4).thenReturn(4);;
        UserDetails ud1 = new UserDetails("E", "K", "E", "K", "R", "S");
        UserDetails ud2 = new UserDetails("M", "R", "Z", "A", "B", "C");
        UserDetails ud3 = new UserDetails("S", "L", "E", "K", "R", "S");
        UserDetails ud4 = new UserDetails("K", "O", "Z", "A", "B", "C");

        when(userDAO.getUser(any(Integer.class))).thenReturn(ud1).thenReturn(ud2).thenReturn(ud3).thenReturn(ud4);
    }
    @Test
    public void shouldReturnAdminList_4Items() throws Exception {
        setUpForReturnAdminList();
        assertEquals(4, new AdminDAOImpl(daoFactory).getAllAdmins().size());
    }

    private List<Admin> createAdmisList() {
        List<Admin> expectedAdmins = new ArrayList<>();
        expectedAdmins.add(new Admin(1, new UserDetails("E", "K", "E", "K", "R", "S")));
        expectedAdmins.add(new Admin(2, new UserDetails("M", "R", "Z", "A", "B", "C")));
        expectedAdmins.add(new Admin(3, new UserDetails("S", "L", "E", "K", "R", "S")));
        expectedAdmins.add(new Admin(4, new UserDetails("K", "O", "Z", "A", "B", "C")));

        return expectedAdmins;
    }

    @Test
    public void shouldReturnTheSameAdmins() throws Exception {
        setUpForReturnAdminList();
        List<Admin> admins = new AdminDAOImpl(daoFactory).getAllAdmins();
        List<Admin> expectedAdmins = createAdmisList();
        for(int i = 0; i<admins.size(); i++) {
            assertIfAdminsTheSame(admins.get(i), expectedAdmins.get(i));
        }
    }
}