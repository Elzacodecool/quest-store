package com.codecool.queststore.DAO;

import java.sql.*;

public class DAOFactoryImpl extends DAOFactory {
    private Connection connection;
    private static final String DRIVER = "org.postgresql.Driver";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/";
    private static final String DB_NAME = "";
    private static final String userName = "";
    private static final String password = "";

    DAOFactoryImpl() {
        this.connection = createConnection();
    }

    public Connection getConnection() {
        return this.connection;
    }

    private Connection createConnection() {
        Connection c = null;
        try {
            Class.forName(DRIVER);
            c = DriverManager.getConnection(DB_URL + DB_NAME, userName, password);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return c;
    }

    public ResultSet execQuery(String query, String ... parameters) {
        PreparedStatement preparedStatement;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            for (int i = 1; i <= parameters.length; i++) {
                preparedStatement.setString(i, parameters[i]);
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
        return new ItemDAOImpl();
    }

    @Override
    public TransactionDAO getTransactionDAO() {
        return new TransactionDAOImpl();
    }
}