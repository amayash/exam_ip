package com.example.exam.folder.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Singer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false, length = 64)
    @NotBlank(message = "name can't be null or empty")
    @Size(min = 3, max = 64)
    private String name;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "singer", cascade = {CascadeType.MERGE,CascadeType.REMOVE})
    private List<Album> albums = new ArrayList<>();

    public Singer(String name) {
        this.name = name;
    }

    public Singer() {
        
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public void setAlbum(Album album) {
        if (album.getSinger().equals(this)) {
            this.albums.add(album);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
