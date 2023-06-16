package com.example.exam.folder.controller;

import com.example.exam.folder.model.User;
import com.example.exam.folder.model.UserRole;

import java.util.ArrayList;
import java.util.List;

public class UserDto {
    private long id;
    private String login;
    private String password;
    private UserRole role;
    private List<RepositoryDto> repositories;

    public UserDto() {
    }

    public UserDto(User user) {
        this.id = user.getId();
        this.login = user.getLogin();
        this.password = user.getPassword();
        this.role = user.getRole();
        this.repositories = new ArrayList<>();
        if (user.getRepositories() != null) {
            repositories = user.getRepositories().stream()
                    .map(RepositoryDto::new).toList();
        }
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

    public void setRole(UserRole role) {
        this.role = role;
    }

    public List<RepositoryDto> getRepositories() {
        return repositories;
    }

    public void setRepositories(List<RepositoryDto> repositories) {
        this.repositories = repositories;
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
