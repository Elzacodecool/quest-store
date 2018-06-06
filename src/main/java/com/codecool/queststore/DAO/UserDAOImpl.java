package com.codecool.queststore.DAO;

import com.codecool.queststore.model.user.AccountType;
import com.codecool.queststore.model.user.Mentor;
import com.codecool.queststore.model.user.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    DAOFactory factory;

    public UserDAOImpl(DAOFactory factory) {
        this.factory = factory;
    }

    /*
	id serial PRIMARY KEY,
    first_name text,
    last_name text,
    email text,
    login text,
    password text,
    account_type text
     */
    @Override
    public void add(User user) {
        StringBuilder query = new StringBuilder();
        query.append("INSER INTO codecooler VALUES (")
                .append("'")
                .append(user.getFirstName())
                .append("',")
                .append(user.getLastName())
                .append("',")
                .append(user.getEmail())
                .append("',")
                .append(user.getPassword())
                .append("',")
                .append(user.getAccountType())
                .append("')");
        factory.execQuery(query.toString());
    }

    @Override
    public void remove(int id) {
        String query = "DELETE FROM codecooler WHERE id=" + id;
        factory.execQuery(query);
    }

    @Override
    public void update(User user) {
        StringBuilder query = new StringBuilder();
        query.append("UPDATE codecooler SET")
                .append("first_name = '")
                .append(user.getFirstName())
                .append("', last_name = '")
                .append(user.getLastName())
                .append("', email = '")
                .append(user.getEmail())
                .append("', login = '")
                .append(user.getLogin())
                .append("', password = '")
                .append(user.getPassword())
                .append("', account_type = '")

                .append("' WHERE id =")
                .append(user.getId());

        factory.execQuery(query.toString());
    }

    @Override
    public User getUser(int id) {
        User result = null;
        String query = "SELECT * FROM codecooler WHERE id = " + id;
        ResultSet resultSet = factory.execQuery(query);
        try {
            result = new User(
                    resultSet.getInt("id"),
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"),
                    resultSet.getString("email"),
                    resultSet.getString("login"),
                    resultSet.getString("password"),
                    getAccTypeValueOf(resultSet.getString("account_type")));

        } catch (SQLException e) {
            e.getErrorCode();
        }
        return result;
    }

    @Override
    public List<User> getAllStudents(Mentor mentor) {
        return null;
    }

    @Override
    public List<User> getAll() {
        List<User> users = null;
        String query = "SELECT * FROM codecooler";
        ResultSet resultSet = factory.execQuery(query);

        try {
            while (resultSet.next()) {
                users.add(new User(
                        resultSet.getInt("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("email"),
                        resultSet.getString("login"),
                        resultSet.getString("password"),
                        getAccTypeValueOf(resultSet.getString("account_type"))));
            }
        } catch (SQLException e) {
            e.getErrorCode();
        }
        return users;
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
