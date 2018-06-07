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
        String query = "SELECT * FROM codecooler JOIN admin ON codecooler.codecooler_id = admin.codecooler_id WHERE codecooler.codecooler_id = ?;";
        ResultSet resultSet = daoFactory.execQuery(query, id);
        return getAdminByUserId(resultSet);
    }

    private Admin getAdminByUserId(ResultSet resultSet) {
        try{
            int user_id = resultSet.getInt("codecooler_id");
            int admin_id = resultSet.getInt("admin_id");
            UserDetails userDetails = userDAO.getUser(user_id);
            return new Admin(admin_id, userDetails);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  null;
    }

    @Override
    public List<Admin> getAllAdmins() {
        List<Admin> admins = new ArrayList<>();
        String query = "SELECT * FROM codecooler JOIN admin ON codecooler.codecooler_id = admin.codecooler_id;";
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
        int user_id = userDAO.add(admin.getUserDetails());
        String query = "INSERT INTO admin (user_id) VALUES(?);";
        daoFactory.execQuery(query, user_id);
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
