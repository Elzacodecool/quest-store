package com.codecool.queststore.DAO;

import com.codecool.queststore.model.user.Admin;
import com.codecool.queststore.model.user.UserDetails;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminDAOImpl implements AdminDAO {
    private  UserDAO userDAO;
    private DAOFactory daoFactory;

    public AdminDAOImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
        this.userDAO = daoFactory.getUserDAO();
    }

    @Override
    public Admin getAdmin(int id) {
        String query = "SELECT codecooler.id FROM codecooler JOIN admin ON codecooler.id = admin.user_id WHERE codecooler.id = ?;";
        try{
            int user_id = daoFactory.execQuery(query, id).getInt("id");
            UserDetails userDetails = daoFactory.getUserDAO().getUser(user_id);
            return new Admin(userDetails);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Admin> getAllAdmins(Admin admin) {
        return null;
    }

    @Override
    public void add(Admin admin) {

    }

    @Override
    public void remove(Admin admin) {

    }

    @Override
    public void update(Admin admin) {

    }
}
