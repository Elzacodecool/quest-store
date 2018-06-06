package com.codecool.applicationprocess.DAO;

import java.sql.*;

public class DatabaseDAOFactory extends DAOFactory {
    private Connection connection;
    private static final String DRIVER = "org.postgresql.Driver";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/";
    private static final String DB_NAME = "eliza";
    private static final String userName = "eliza";
    private static final String password = "password";

    public DatabaseDAOFactory() {
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
            Statement stmt = c.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return c;
    }

    public ResultSet execQuery(String query) {
        try {
            return this.connection.createStatement().executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    public MentorDAO getMentorDAO() {
        return new DatabaseMentorDAO(this);
    }

    public ApplicantDAO getApplicantDAO() {
        return new DatabaseApplicantDAO(this);
    }
}
