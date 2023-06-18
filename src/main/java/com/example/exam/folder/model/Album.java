package com.example.exam.folder.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false, length = 64)
    @NotBlank(message = "name can't be null or empty")
    @Size(min = 3, max = 64)
    private String name;
    @Column(nullable = false, length = 4)
    @NotNull(message = "releaseYear can't be null or empty")
    private Integer releaseYear;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "album", cascade = {CascadeType.MERGE,CascadeType.REMOVE})
    private List<Sing> sings = new ArrayList<>();
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "singer_fk")
    protected Singer singer;

    public Album(String name, Integer year) {
        this.name = name;
        this.releaseYear = year;
    }

    public Album() {

    }

    public Singer getSinger() {
        return singer;
    }

    public void setSinger(Singer singer) {
        this.singer = singer;
    }

    public List<Sing> getSings() {
        return sings;
    }

    public void setSing(Sing sing) {
        if (sing.getAlbum().equals(this)) {
            this.sings.add(sing);
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

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }
}
