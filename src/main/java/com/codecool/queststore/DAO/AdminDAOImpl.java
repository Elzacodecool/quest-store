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
        ResultSet resultSet = daoFactory.execQuery(query, id);
        return getAdminByUserId(resultSet);
    }

    private Admin getAdminByUserId(ResultSet resultSetUserId) {
       try{
            int user_id = resultSetUserId.getInt("id");
            UserDetails userDetails = userDAO.getUser(user_id);
            return new Admin(userDetails);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  null;
    }

    @Override
    public List<Admin> getAllAdmins() {
        List<Admin> admins = new ArrayList<>();
        String query = "SELECT codecooler.id FROM codecooler JOIN admin ON codecooler.id = admin.user_id;";
        try {
            ResultSet resultSet = daoFactory.execQuery(query);
            while (resultSet.next()) {
                admins.add(getAdminByUserId(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return admins;
    }

    @Override
    public void add(Admin admin) {

    }

    @Override
    public void remove(Admin admin) {
        userDAO.remove(admin.getUserDetails());
    }

    @Override
    public void update(Admin admin) {
        userDAO.update(admin.getUserDetails());
    }
}
