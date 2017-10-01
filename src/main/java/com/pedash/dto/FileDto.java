package com.pedash.dto;

import com.pedash.entities.RemoteDoc;

/**
 * Created by Yuliya Pedash on 07.06.2017.
 */
public class FileDto  {
    RemoteDoc fileObj;
    String content;

    public RemoteDoc getFileObj() {
        return fileObj;
    }

    public void setFileObj(RemoteDoc fileObj) {
        this.fileObj = fileObj;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
