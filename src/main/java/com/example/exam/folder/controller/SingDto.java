package com.example.exam.folder.controller;

import com.example.exam.folder.model.Sing;
import org.springframework.format.annotation.DateTimeFormat;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

public class SingDto {
    private long id;
    private String name;
    private String albumName;
    private Integer duration;
    private Long albumId;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime timestamp;

    public SingDto() {
    }

    public SingDto(Sing sing) {
        this.id = sing.getId();
        this.name = sing.getName();
        this.duration = sing.getDuration();
        this.timestamp = sing.getTimestamp();
        this.albumId = sing.getAlbum().getId();
        this.albumName = sing.getAlbum().getName();
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
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

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
