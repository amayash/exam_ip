package com.example.exam.folder.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Commit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false, unique = true, length = 64)
    @NotBlank(message = "name can't be null or empty")
    @Size(min = 3, max = 64)
    private String name;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "branch_fk")
    protected Branch branch;
    @OneToMany(mappedBy = "commit", fetch = FetchType.LAZY)
    private List<File> files;

    public Commit(String name) {
        this.name = name;
        files = new ArrayList<>();
    }

    public Commit() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Commit commit = (Commit) o;
        return Objects.equals(id, commit.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFile(File file) {
        if (file.getCommit().equals(this)) {
            this.files.add(file);
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

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }
}
