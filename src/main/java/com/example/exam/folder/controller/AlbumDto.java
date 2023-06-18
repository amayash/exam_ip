package com.example.exam.folder.controller;

import com.example.exam.folder.model.Album;

import java.util.List;

public class AlbumDto {
    private long id;
    private String name;
    private String singerName;
    private Long singerId;
    private Integer releaseYear;
    private List<SingDto> sings;

    public AlbumDto() {
    }

    public AlbumDto(Album album) {
        this.id = album.getId();
        this.name = album.getName();
        this.releaseYear = album.getReleaseYear();
        this.singerName = album.getSinger().getName();
        this.singerId = album.getSinger().getId();
        if (album.getSings() != null && album.getSings().size() > 0)
            this.sings = album.getSings().stream().map(SingDto::new).toList();
    }

    public Long getSingerId() {
        return singerId;
    }

    public void setSingerId(Long singerId) {
        this.singerId = singerId;
    }

    public String getSingerName() {
        return singerName;
    }

    public void setSingerName(String singerName) {
        this.singerName = singerName;
    }

    public List<SingDto> getSings() {
        return sings;
    }

    public void setSings(List<SingDto> sings) {
        this.sings = sings;
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

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }
}
