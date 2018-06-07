package com.codecool.queststore.DAO;

import java.sql.*;

public class DAOFactoryImpl extends DAOFactory {
    private Connection connection;
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/";
    private static final String DB_NAME = "queststore";
    private static final String userName = "codecooler";
    private static final String password = "123";

    public DAOFactoryImpl() {
        this.connection = createConnection();
    }

    public Connection getConnection() {
        return this.connection;
    }

    private Connection createConnection() {
        Connection c = null;
        try {
            c = DriverManager.getConnection(DB_URL + DB_NAME, userName, password);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return c;
    }

    public ResultSet execQuery(String query) {
        PreparedStatement preparedStatement;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public ResultSet execQuery(String query, String... parameters) {
        PreparedStatement preparedStatement;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            for (int i = 0; i < parameters.length; i++) {
                preparedStatement.setString(i + 1, parameters[i]);
            }
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public ResultSet execQuery(String sqlQuery, int numberParameter, String...parameters) {
        PreparedStatement preparedStatement;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(sqlQuery);
            int index = 1;
            for (String parameter : parameters) {
                preparedStatement.setString(index, parameter);
                index++;
            }
            preparedStatement.setInt(index, numberParameter);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public ResultSet execQueryInt(String sqlQuery, int...numberParameter) {
        PreparedStatement preparedStatement;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(sqlQuery);
            int index = 1;
            for (int parameter : numberParameter) {
                preparedStatement.setInt(index, parameter);
                index++;
            }
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public ItemDAO getItemDAO() {
        return new ItemDAOImpl(this) {
        };
    }

    @Override
    public TransactionDAO getTransactionDAO() {
        return new TransactionDAOImpl(this);
    }

    @Override
    public UserDAO getUserDAO() {
        return new UserDAOImpl(this);
    }

    @Override
    public ClassDAO getClassDAO() {
        return new ClassDAOImpl(this);
    }
}
