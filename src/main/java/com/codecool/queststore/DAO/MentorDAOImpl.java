package com.codecool.queststore.DAO;

import com.codecool.queststore.model.classRoom.ClassRoom;
import com.codecool.queststore.model.user.Mentor;
import com.codecool.queststore.model.user.UserDetails;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MentorDAOImpl implements MentorDAO {
    private UserDAO userDAO;
    private DAOFactory daoFactory;

    public MentorDAOImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
        this.userDAO = daoFactory.getUserDAO();
    }


    @Override
    public Mentor getMentor(int id) {
        String query = "SELECT * FROM codecooler JOIN mentor ON codecooler.codecooler_id = mentor.codecooler_id " +
                "WHERE codecooler.codecooler_id = ?;";
        ResultSet resultSet = daoFactory.execQuery(query, id);
        return getMentorByUserId(resultSet);
    }

    private Mentor getMentorByUserId(ResultSet resultSet) {
        try{
            int user_id = resultSet.getInt("codecooler_id");
            int mentor_id = resultSet.getInt("mentor_id");
            UserDetails userDetails = userDAO.getUser(user_id);
            return new Mentor(mentor_id, userDetails);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Mentor> getAllMentors() {
        String query = "SELECT * FROM codecooler JOIN mentor ON codecooler.codecooler_id = mentor.codecooler_id;";
        ResultSet resultSet = daoFactory.execQuery(query);
        return getMentorsByResultSet(resultSet);
    }

    private List<Mentor> getMentorsByResultSet(ResultSet resultSet) {
        List<Mentor> mentors = new ArrayList<>();
        try {
            while (resultSet.next()) {
                mentors.add(getMentorByUserId(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mentors;
    }

    @Override
    public List<Mentor> getMentorsFromClass(ClassRoom classRoom) {
        String query = "SELECT * FROM mentor JOIN mentor_class ON mentor.mentor_id = mentor_class.mentor_id " +
                " WHERE mentor_class.class_id = ?;";
        ResultSet resultSet = daoFactory.execQuery(query, classRoom.getId());
        return getMentorsByResultSet(resultSet);
    }

    @Override
    public void add(Mentor mentor) {
        int user_id = userDAO.add(mentor.getUserDetails());
        String query = "INSERT INTO mentor (codecooler_id) VALUES(?);";
        daoFactory.execQuery(query, user_id);
    }

    @Override
    public void remove(Mentor mentor) {
        userDAO.remove(mentor.getUserDetails());
    }

    @Override
    public void update(Mentor mentor) {
        userDAO.update(mentor.getUserDetails());
    }
}
