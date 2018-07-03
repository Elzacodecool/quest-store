package com.codecool.queststore.model;

import java.util.HashMap;
import java.util.Map;

public class SingletonAcountContainer {

        private static SingletonAcountContainer instance;
        private Map<String, Integer> mapAccount;

        private SingletonAcountContainer() {
            mapAccount = new HashMap<>();
        }

        public static SingletonAcountContainer getInstance() {
            if (instance == null) {
                instance = new SingletonAcountContainer();
            }

            return instance;
        }

        public void addAccount(String sessionId, int id) {
            mapAccount.put(sessionId, id);
        }

        public int getCodecoolerId(String sessionId) {
            return mapAccount.get(sessionId);
        }

        public boolean checkIfContains(String sessionId) {
            return mapAccount.containsKey(sessionId);
        }

}
