package com.codecool.queststore.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDAOImpl implements AccountDAO {

    private  UserDAO userDAO;
    private DAOFactory daoFactory;

    public AccountDAOImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
        this.userDAO = daoFactory.getUserDAO();
    }


    @Override
    public boolean validateAccount(String login, String password) {
        String query = "SELECT codecooler_id FROM codecooler WHERE login = ? AND password = ?;";
        int id = -1;
        int INDEX_CODECOOLER_ID = 1;
        try {
            ResultSet resultSet = daoFactory.execQuery(query, login, password);
            resultSet.next();
            id = resultSet.getInt(INDEX_CODECOOLER_ID);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return id > 0;
    }

    @Override
    public String getAccountType(String login, String password) {
        String query = "SELECT account_type FROM codecooler WHERE login = ? AND password = ?;";
        String account_type = null;
        int INDEX_ACCOUNT_TYPE = 1;
        try {
            ResultSet resultSet = daoFactory.execQuery(query, login, password);
            resultSet.next();
            account_type = resultSet.getString(INDEX_ACCOUNT_TYPE);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return account_type;
    }

    @Override
    public int getCodecoolerId(String login, String password) {
        String query = "SELECT codecooler_id FROM codecooler WHERE login = ? AND password = ?;";
        int id = 0;
        int INDEX_CODECOOLER_ID = 1;
        try {
            ResultSet resultSet = daoFactory.execQuery(query, login, password);
            resultSet.next();
            id = resultSet.getInt(INDEX_CODECOOLER_ID);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return id;
    }

    @Override
    public String getAccountType(int id) {
        String query = "SELECT account_type FROM codecooler WHERE codecooler_id = ?;";
        String account_type = null;
        int INDEX_ACCOUNT_TYPE = 1;
        try {
            ResultSet resultSet = daoFactory.execQuery(query, id);
            resultSet.next();
            account_type = resultSet.getString(INDEX_ACCOUNT_TYPE);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return account_type;
    }


}
