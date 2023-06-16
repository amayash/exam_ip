package com.example.exam.folder.controller;

import com.example.exam.folder.model.Branch;
import com.example.exam.folder.model.Repository;

import java.util.List;

public class RepositoryDto {
    private long id;
    private String name;
    private String description;
    private String user;
    private Long userId;
    private List<BranchDto> branches;

    public RepositoryDto() {
    }

    public RepositoryDto(Repository repository) {
        this.id = repository.getId();
        this.name = repository.getName();
        this.description = repository.getDescription();
        this.userId = repository.getUser().getId();
        this.user = repository.getUser().getLogin();
        if (repository.getBranches() != null) {
            branches = repository.getBranches().stream()
                    .map(BranchDto::new).toList();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<BranchDto> getBranches() {
        return branches;
    }

    public void setBranches(List<BranchDto> branches) {
        this.branches = branches;
    }
}
