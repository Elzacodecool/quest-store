package com.codecool.queststore.details;

import java.util.List;
import java.util.ArrayList;

public enum Access {
    STUDENT {
        @Override
        public List<Privilege> initPrivileges() {
            List<Privilege> privileges = new ArrayList<>();

            privileges.add(Privilege.EXIT);

            return privileges;
        }
    },

    ADMIN {
        @Override
        public List<Privilege> initPrivileges() {
            List<Privilege> privileges = new ArrayList<>();

            privileges.add(Privilege.EXIT);

            return privileges;
        }
    },

    MENTOR {
        @Override
        public List<Privilege> initPrivileges() {
            List<Privilege> privileges = new ArrayList<>();

            privileges.add(Privilege.EXIT);

            return privileges;
        }
    };

    private List<Privilege> privileges = initPrivileges();
    public abstract List<Privilege> initPrivileges();
    public List<Privilege> getPrivileges() {
        return privileges;
    }
}
