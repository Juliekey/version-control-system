package com.pedash.entities;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * Created by Yuliya Pedash on 21.06.2017.
 */
public class Revision {
    Integer id;
    Integer remoteDocId;
    Status status;
    String change;
    Date dateTime;
    Integer userId;

    public Revision() {
    }

    public Revision(Integer remoteDocId, Status status, String change, Integer userId) {
        this.remoteDocId = remoteDocId;
        this.status = status;
        this.change = change;
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }
}
