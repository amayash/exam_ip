package com.example.exam.folder.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false, unique = true, length = 64)
    @NotBlank(message = "login can't be null or empty")
    @Size(min = 3, max = 64)
    private String login;
    @Column(nullable = false, length = 64)
    @NotBlank(message = "password can't be null or empty")
    @Size(min = 6, max = 64)
    private String password;
    private UserRole role;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = {CascadeType.MERGE,CascadeType.REMOVE})
    private List<Repository> repositories;
    public User() {
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
        this.role = UserRole.USER;
    }

    public User(String login, String password, UserRole role) {
        this.login = login;
        this.password = password;
        this.repositories = new ArrayList<>();
        this.role = role;
    }

    public User(UserSignupDto userSignupDto) {
        this.login = userSignupDto.getLogin();
        this.password = userSignupDto.getPassword();
        this.repositories = new ArrayList<>();
        this.role = UserRole.USER;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User u = (User) o;
        return Objects.equals(id, u.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "User {" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public UserRole getRole() {
        return role;
    }

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Repository> getRepositories() {
        return repositories;
    }

    public void setRepository(Repository repository) {
        if (repository.getUser().equals(this)) {
            this.repositories.add(repository);
        }
    }
}
