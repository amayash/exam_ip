package com.example.exam.folder.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false, unique = true, length = 64)
    @NotBlank(message = "name can't be null or empty")
    @Size(min = 3, max = 64)
    protected String name;
    @NotNull(message = "maxCount can't be null or empty")
    @Column
    protected Integer maxCount;
    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY)
    private List<CartBook> carts;

    @ManyToOne
    @JoinColumn(name = "category_fk")
    protected Category category;

    @ManyToMany(mappedBy = "books", fetch = FetchType.LAZY)
    private Set<Author> authors = new HashSet<>();

    @PreRemove
    public void removeAuthors() {
        for (var author : authors) {
            author.getBooks().remove(this);
        }
    }

    public Book() {
    }

    public Book(String name, Integer maxCount) {
        this.name = name;
        this.maxCount = maxCount;
    }

    public Book(Book book) {
        this.id = book.getId();
        this.name = book.getName();
        this.carts = book.getCarts();
        this.authors = book.getAuthors();
        this.category = book.getCategory();
        this.maxCount = book.getMaxCount();
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthor(Author author) {
        authors.add(author);
        if (!author.getBooks().contains(this)) {
            author.setBook(this);
        }
    }

    public void removeAuthor(Author p) {
        authors.remove(p);
        if (p.getBooks().contains(this)) {
            p.removeBook(this);
        }
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
        if (!category.getBooks().contains(this)) {
            category.setBook(this);
        }
    }

    public List<CartBook> getCarts() {
        return carts;
    }

    public void setCarts(List<CartBook> carts) {
        this.carts = carts;
    }

    public void addCart(CartBook cartBook){
        if (carts == null){
            carts = new ArrayList<>();
        }
        if (!carts.contains(cartBook)) {
            this.carts.add(cartBook);
        }
    }

    public void removeCart(CartBook cartBook){
        if (carts.contains(cartBook))
            this.carts.remove(cartBook);
    }

    public void setMaxCount(Integer maxCount) {
        this.maxCount = maxCount;
    }

    public Integer getMaxCount() {
        return maxCount;
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
