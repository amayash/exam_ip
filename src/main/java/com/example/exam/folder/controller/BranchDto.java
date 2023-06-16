package com.example.exam.folder.controller;

import com.example.exam.folder.model.Branch;

import java.util.List;

public class BranchDto {
    private long id;
    private String name;
    private Long repositoryId;
    private List<CommitDto> commits;

    public BranchDto() {
    }

    public BranchDto(Branch branch) {
        this.id = branch.getId();
        this.name = branch.getName();
        this.repositoryId = branch.getRepository().getId();
        if (branch.getCommits() != null) {
            commits = branch.getCommits().stream()
                    .map(CommitDto::new).toList();
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getRepositoryId() {
        return repositoryId;
    }

    public void setRepositoryId(Long repositoryId) {
        this.repositoryId = repositoryId;
    }

    public List<CommitDto> getCommits() {
        return commits;
    }

    public void setCommits(List<CommitDto> commits) {
        this.commits = commits;
    }
}
