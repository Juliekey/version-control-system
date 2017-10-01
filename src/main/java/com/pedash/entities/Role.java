package com.pedash.entities;

import java.util.Objects;

/**
 * Created by Yuliya Pedash on 04.05.2017.
 */
public enum Role {
    ADMIN(1, "ADMIN"),
    USER(2, "USER");

    private Integer id;
    private String name;
    private static String WRONG_ID_ERROR_MSG = "Wrong docId: ";

    Role(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }


    public Integer getId() {
        return id;
    }

    /**
     * This method gets <code>Role</code> object by docId
     *
     * @param id docId of role
     * @return Role object
     */
    public static Role getRoleFromId(Integer id) {
        Role[] roles = values();
        for (Role role : roles) {
            if (Objects.equals(role.getId(), id)) {
                return role;
            }
        }
        throw new IllegalArgumentException(WRONG_ID_ERROR_MSG + id);
    }

    public static Role getRoleByName(String name) {
        Role[] roles = values();
        for (Role role : roles) {
            if (Objects.equals(role.getName(), name.toUpperCase())) {
                return role;
            }
        }
        return null;
    }

}
