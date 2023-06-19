package com.example.exam.folder.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
public class Playlist {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false, unique = true, length = 64)
    @NotBlank(message = "name can't be null or empty")
    @Size(min = 3, max = 64)
    private String name;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_fk")
    protected User user;
    @ManyToMany
    @JoinTable(name = "playlist_sing",
            joinColumns = @JoinColumn(name = "playlist_fk"),
            inverseJoinColumns = @JoinColumn(name = "sing_fk"))
    private Set<Sing> sings = new HashSet<>();

    public Playlist() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Playlist r = (Playlist) o;
        return Objects.equals(id, r.id);
    }

    public Playlist(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, user);
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Sing> getSings() {
        return sings;
    }

    public void setSing(Sing p) {
        sings.add(p);
        if (!p.getPlaylists().contains(this)) {
            p.setPlaylist(this);
        }
    }

    public void removeSing(Sing p) {
        sings.remove(p);
        if (p.getPlaylists().contains(this)) {
            p.removePlaylist(this);
        }
    }
}
