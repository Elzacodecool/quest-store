package com.codecool.queststore.DAO;

import com.codecool.queststore.model.inventory.Category;
import com.codecool.queststore.model.inventory.Item;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDAOImpl implements  ItemDAO {

    private DAOFactory daoFactory;
    private final int INDEX_ID = 1;
    private final int INDEX_NAME = 2;
    private final int INDEX_DESCRIPTION = 3;
    private final int INDEX_PRICE = 4;
    private final int INDEX_CATEGORY = 5;

    public ItemDAOImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    @Override
    public Item get(int id) {
        Item item = null;
        String sqlQuery = "SELECT * FROM item WHERE id = ?;";
        ResultSet resultSet = daoFactory.execQuery(sqlQuery, id);
        try {
            item = getItemByResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return item;
    }

    @Override
    public List<Item> getItems() {
        List<Item> itemList = new ArrayList<>();
        String sqlQuery = "SELECT * FROM item;";
        ResultSet resultSet = daoFactory.execQuery(sqlQuery);
        try {
            itemList.add(getItemByResultSet(resultSet));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return itemList;
    }

    @Override
    public List<Item> getQuests() {
        List<Item> itemList = new ArrayList<>();
        String sqlQuery = "SELECT * FROM item WHERE category = \'Quest\';";
        ResultSet resultSet = daoFactory.execQuery(sqlQuery);
        try {
            itemList.add(getItemByResultSet(resultSet));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return itemList;
    }

    @Override
    public void add(Item item) {
        String sqlQuery = "INSERT INTO item (name, decription, category, price) VALUES (?, ?, ?, ?)";
        daoFactory.execQuery(sqlQuery, item.getPrice(), item.getName(), item.getDescription(), item.getCategory().getName());
    }

    @Override
    public void remove(Item item) {
        String sqlQuery = "DELETE FROM item WHERE id = ?";
        daoFactory.execQueryInt(sqlQuery, item.getId());
    }

    @Override
    public void update(Item item) {
        String sqlQuery = "UPDATE item SET price = ? WHERE id = ?";
        daoFactory.execQueryInt(sqlQuery, item.getPrice(), item.getId());
        sqlQuery = "UPDATE item SET name = ?, decription = ?, category = ?) WHERE id = ?";
        daoFactory.execQuery(sqlQuery, item.getId(), item.getName(), item.getDescription(), item.getCategory().getName());
    }

    private Item getItemByResultSet(ResultSet resultSet) throws SQLException {
        Item item = null;
        Category category = new Category(resultSet.getString(INDEX_CATEGORY));
        item = new Item(resultSet.getInt(INDEX_ID), resultSet.getString(INDEX_NAME), resultSet.getString(INDEX_DESCRIPTION), resultSet.getInt(INDEX_PRICE), category);

        return item;
    }
}
