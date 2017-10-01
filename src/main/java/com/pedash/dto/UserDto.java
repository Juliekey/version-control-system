package com.pedash.dto;

/**
 * Created by Yuliya Pedash on 06.06.2017.
 */
public class UserDto {
    Integer id;
    String name;
    String group;

    public UserDto(Integer id, String name, String group) {
        this.id = id;
        this.name = name;
        this.group = group;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
