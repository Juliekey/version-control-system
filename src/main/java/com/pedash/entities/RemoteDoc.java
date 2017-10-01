package com.pedash.entities;

/**
 * Created by Yuliya Pedash on 07.06.2017.
 */
public class RemoteDoc {
    Integer docId;
    String name;
    String content;

    public Integer getDocId() {
        return docId;
    }

    public void setDocId(Integer docId) {
        this.docId = docId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
