package com.example.exam.folder.controller;

import com.example.exam.folder.model.User;
import com.example.exam.folder.model.UserRole;

import java.util.ArrayList;

public class UserDto {
    private long id;
    private String login;
    private String password;
    private UserRole role;

    public UserDto() {
    }

    public UserDto(User User) {
        this.id = User.getId();
        this.login = User.getLogin();
        this.password = User.getPassword();
        this.role = User.getRole();
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public UserRole getRole() {
        return role;
    }
}
