package com.pedash.entities;

import java.util.Objects;

/**
 * Created by Yuliya Pedash on 21.06.2017.
 */
public enum Status {
    None(0, "none"),
    DocDeleted(1, "doc_deleted"),
    DocAdded(2, "doc_added"),
    Deleted(3, "deleted"),
    Added(4, "added"),
    Updated(9, "doc_updated");




    private Integer id;
    private String name;
    private static String WRONG_ID_ERROR_MSG = "Wrong docId: ";

    Status(Integer id, String name) {
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
     * This method gets <code>Status</code> object by docId
     *
     * @param id docId of status
     * @return Status object
     */
    public static Status getStatusFromId(Integer id) {
        Status[] statuses = values();
        for (Status status : statuses) {
            if (Objects.equals(status.getId(), id)) {
                return status;
            }
        }
        throw new IllegalArgumentException(WRONG_ID_ERROR_MSG + id);
    }

    public static Status getStatusByName(String name) {
        Status[] statuses = values();
        for (Status status : statuses) {
            if (Objects.equals(status.getName(), name.toUpperCase())) {
                return status;
            }
        }
        return null;
    }

}
