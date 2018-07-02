package com.codecool.queststore.DAO;

public interface AccountDAO {

    boolean validateAccount(String login, String password);
    String getAccountType(String login, String password);
    int getCodecoolerId(String login, String password);
    String getAccountType(int id);
}
