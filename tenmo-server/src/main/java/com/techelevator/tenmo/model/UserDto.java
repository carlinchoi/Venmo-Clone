package com.techelevator.tenmo.model;

public class UserDto {
    private int id;
    private String username;

    public UserDto(int id, String username) {
        this.id = id;
        this.username = username;
    }

    public UserDto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
