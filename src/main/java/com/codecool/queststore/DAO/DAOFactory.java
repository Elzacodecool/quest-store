package com.codecool.applicationprocess.DAO;

import java.sql.Connection;
import java.sql.ResultSet;

public abstract class DAOFactory {
    public abstract MentorDAO getMentorDAO();
    public abstract ApplicantDAO getApplicantDAO();
    public abstract ResultSet execQuery(String query);
    public abstract Connection getConnection();

    public static DAOFactory getDAOFactory() {
        return new DatabaseDAOFactory();
    }
}
