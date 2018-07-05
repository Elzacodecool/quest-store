package com.codecool.queststore.DAO;

import com.codecool.queststore.model.user.AccountType;
import com.codecool.queststore.model.user.Mentor;
import com.codecool.queststore.model.user.Student;
import com.codecool.queststore.model.user.UserDetails;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    private DAOFactory factory;

    public UserDAOImpl(DAOFactory factory) {
        this.factory = factory;
    }

    @Override
    public int add(UserDetails userDetails) {
        Integer userDetailsId = null;
        String query = "INSERT INTO codecooler (first_name, last_name, email, login, password, account_type) VALUES (?,?,?,?,?,?) RETURNING codecooler_id;";

        try {
            ResultSet rs = factory.execQuery(query,
                    userDetails.getFirstName(),
                    userDetails.getLastName(),
                    userDetails.getEmail(),
                    userDetails.getLogin(),
                    userDetails.getPassword(),
                    userDetails.getAccountType()
            );
            rs.next();
            userDetailsId = rs.getInt(1);
        } catch (SQLException e) {
            System.out.println("Error: " + e.getErrorCode());
        }
        return userDetailsId;
    }

    @Override
    public void remove(int id) {
        String query = "DELETE FROM codecooler WHERE codecooler_id = ?;";
        factory.execQuery(query, id);
    }

    @Override
    public void update(UserDetails userDetails) {
        String query = "UPDATE codecooler SET first_name = ?, last_name = ?, email = ?, login = ?, password = ? WHERE codecooler_id = ?";
        try {
            PreparedStatement ps = factory.getConnection().prepareStatement(query);
            ps.setString(1, userDetails.getFirstName());
            ps.setString(2, userDetails.getLastName());
            ps.setString(3, userDetails.getEmail());
            ps.setString(4, userDetails.getLogin());
            ps.setString(5, userDetails.getPassword());
            ps.setInt(6, userDetails.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getSQLState());
        }
    }

    @Override
    public UserDetails getUser(int id) {
        UserDetails userDetails = null;
        String query = "SELECT * FROM codecooler WHERE codecooler_id = ?;";
        ResultSet resultSet = factory.execQueryInt(query, id);
        //TODO: Extract to a method (lines 68-75 & 97-104)
        try {
            resultSet.next();
            userDetails = new UserDetails(
                    resultSet.getInt("codecooler_id"),
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"),
                    resultSet.getString("email"),
                    resultSet.getString("login"),
                    resultSet.getString("password"),
                    resultSet.getString("account_type"));
        } catch (SQLException e) {
            e.getErrorCode();
        }
        return userDetails;
    }

    @Override
    public UserDetails getUserByLogin(String login) {
        UserDetails userDetails = null;
        String query = "SELECT * FROM codecooler WHERE login = ?;";
        ResultSet resultSet = factory.execQuery(query, login);
        //TODO: Extract to a method (lines 68-75 & 97-104)
        try {
            resultSet.next();
            userDetails = new UserDetails(
                    resultSet.getInt("codecooler_id"),
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"),
                    resultSet.getString("email"),
                    resultSet.getString("login"),
                    resultSet.getString("password"),
                    resultSet.getString("account_type"));
        } catch (SQLException e) {
            e.getErrorCode();
        }
        return userDetails;
    }

    @Override
    public List<UserDetails> getAllStudents(Mentor mentor) {
        //TODO: Implement!
        return null;
    }

    @Override
    public List<UserDetails> getAll() {
        List<UserDetails> userDetails = new ArrayList<>();
        String query = "SELECT * FROM codecooler;";
        ResultSet resultSet = factory.execQuery(query);

        try {
            while (resultSet.next()) {
                userDetails.add(new UserDetails(
                        resultSet.getInt("codecooler_id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("email"),
                        resultSet.getString("login"),
                        resultSet.getString("password"),
                        resultSet.getString("account_type")));
            }
        } catch (SQLException e) {
            e.getErrorCode();
        }
        return userDetails;
    }

    private AccountType getAccTypeValueOf(String s) {
        AccountType type = null;
        switch (s) {
            case "ADMIN":
                type = AccountType.ADMIN;
                break;
            case "MENTOR":
                type = AccountType.MENTOR;
                break;
            case "STUDENT":
                type = AccountType.STUDENT;
                break;
        }
        return type;
    }
}
