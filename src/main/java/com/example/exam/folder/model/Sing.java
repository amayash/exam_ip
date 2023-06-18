package com.example.exam.folder.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Sing {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false, length = 64)
    @NotBlank(message = "name can't be null or empty")
    @Size(min = 3, max = 64)
    private String name;
    @Column(nullable = false, length = 6)
    @NotNull(message = "duration can't be null or empty")
    private Integer duration;
    @NotNull(message = "timestamp can't be null or empty")
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    protected LocalDateTime timestamp;
    @ManyToMany(mappedBy = "sings")
    private List<Playlist> playlists = new ArrayList<>();
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "album_fk")
    private Album album;

    public Sing(String name, Integer duration, LocalDateTime timestamp) {
        this.name = name;
        this.duration = duration;
        this.timestamp = timestamp;
    }

    public Sing() {

    }
    
    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
        if (!album.getSings().contains(this)) {
            album.setSing(this);
        }
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public List<Playlist> getPlaylists() {
        return playlists;
    }

    public void setPlaylist(Playlist p) {
        playlists.add(p);
        if (!p.getSings().contains(this)) {
            p.setSing(this);
        }
    }

    public void removePlaylist(Playlist p) {
        playlists.remove(p);
        if (p.getSings().contains(this)) {
            p.removeSing(this);
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

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }
}
