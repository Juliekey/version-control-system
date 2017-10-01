package com.pedash.entities;

/**
 * Created by Yuliya Pedash on 21.06.2017.
 */
public class LocalDoc extends RemoteDoc {
    Integer remoteDocId;
    Status status;
    Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public LocalDoc() {
    }


    public Integer getRemoteDocId() {
        return remoteDocId;
    }

    public void setRemoteDocId(Integer remoteDocId) {
        this.remoteDocId = remoteDocId;
    }


    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
