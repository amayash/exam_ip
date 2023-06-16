package com.example.exam.folder.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.*;

@Entity
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false, unique = true, length = 64)
    @NotBlank(message = "name can't be null or empty")
    @Size(min = 3, max = 64)
    private String name;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "repository_fk")
    protected Repository repository;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "branch", cascade = {CascadeType.MERGE,CascadeType.REMOVE})
    private List<Commit> commits;

    public Branch(String name) {
        this.name = name;
        commits = new ArrayList<>();
    }

    public Branch() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Branch branch = (Branch) o;
        return Objects.equals(id, branch.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
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

    public Repository getRepository() {
        return repository;
    }

    public void setRepository(Repository repository) {
        this.repository = repository;
        if (!repository.getBranches().contains(this)) {
            repository.setBranch(this);
        }
    }

    public List<Commit> getCommits() {
        return commits;
    }

    public void setCommit(Commit commit) {
        if (commit.getBranch().equals(this)) {
            this.commits.add(commit);
        }
    }
}
