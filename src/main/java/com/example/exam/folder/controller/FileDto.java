package com.example.exam.folder.controller;

import com.example.exam.folder.model.File;

public class FileDto {
    private long id;
    private String name;
    private String content;

    public FileDto() {
    }

    public FileDto(File file) {
        this.id = file.getId();
        this.name = file.getName();
        this.content = file.getContent();
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
