package com.example.exam.folder.controller;

import com.example.exam.folder.model.Playlist;

import java.sql.Date;
import java.util.List;

public class PlaylistDto {
    private long id;
    private String name;
    private Long userId;
    private List<SingDto> sings;

    public PlaylistDto(Playlist playlist) {
        this.id = playlist.getId();
        this.name = playlist.getName();
        this.userId = playlist.getUser().getId();
        this.sings = playlist.getSings().stream().map(SingDto::new).toList();
    }

    public PlaylistDto() {
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<SingDto> getSings() {
        return sings;
    }

    public void setSings(List<SingDto> sings) {
        this.sings = sings;
    }
}
