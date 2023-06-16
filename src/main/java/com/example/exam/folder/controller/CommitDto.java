package com.example.exam.folder.controller;

import com.example.exam.folder.model.Commit;

import java.util.List;

public class CommitDto {
    private long id;
    private String name;
    private List<FileDto> files;

    public CommitDto() {
    }

    public CommitDto(Commit commit) {
        this.id = commit.getId();
        this.name = commit.getName();
        if (commit.getFiles() != null) {
            files = commit.getFiles().stream()
                    .map(FileDto::new).toList();
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

    public List<FileDto> getFiles() {
        return files;
    }

    public void setFiles(List<FileDto> files) {
        this.files = files;
    }
}
