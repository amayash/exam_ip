package com.example.exam.folder.controller;

import com.example.exam.folder.model.Singer;

import java.util.List;

public class SingerDto {
    private long id;
    private String name;

    public SingerDto(Singer singer) {
        this.id = singer.getId();
        this.name = singer.getName();
    }

    public SingerDto() {
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
}
