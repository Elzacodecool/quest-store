package com.codecool.queststore.controller;

import com.codecool.queststore.DAO.DAOFactory;
import com.codecool.queststore.DAO.DAOFactoryImpl;
import com.codecool.queststore.DAO.ItemDAO;
import com.codecool.queststore.DAO.TransactionDAO;
import com.codecool.queststore.model.Transaction;
import com.codecool.queststore.model.inventory.Item;

import java.util.Map;

public class QuestManagment {
    private int classId;
    private int questId;
    private int studentId;
    private DAOFactory daoFactory;
    private ItemDAO itemDAO;
    private TransactionDAO transactionDAO;

    public QuestManagment() {
        daoFactory = new DAOFactoryImpl();
        itemDAO = daoFactory.getItemDAO();
        transactionDAO = daoFactory.getTransactionDAO();
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public void setQuestId(int questId) {
        this.questId = questId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getClassId() {
        return classId;
    }

    public void addQuestToStudents(Map<String, String> studentsIdMap) {
        Item item = itemDAO.get(questId);
        for (String stringId : studentsIdMap.keySet()) {
            int studentId = Integer.parseInt(studentsIdMap.get(stringId));
            System.out.println(studentId);
            Transaction transaction = new Transaction(studentId, item, item.getPrice());
            transactionDAO.add(transaction);
        }
        System.out.println(transactionDAO.getTransactionByUser(17));
    }
}
