package com.example.exam.folder.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
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
    @ManyToMany
    @JoinTable(name = "singer_sing",
            joinColumns = @JoinColumn(name = "singer_fk"),
            inverseJoinColumns = @JoinColumn(name = "sing_fk"))
    private Set<Sing> sings = new HashSet<>();

    public Singer(String name) {
        this.name = name;
    }

    public Singer() {
        
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

    public Set<Sing> getSings() {
        return sings;
    }

    public void setSing(Sing p) {
        sings.add(p);
        if (!p.getSingers().contains(this)) {
            p.setSinger(this);
        }
    }

    public void removeSing(Sing p) {
        sings.remove(p);
        if (p.getSingers().contains(this)) {
            p.removeSinger(this);
        }
    }
}
