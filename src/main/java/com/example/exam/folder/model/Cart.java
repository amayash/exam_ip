package com.example.exam.folder.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToMany(mappedBy = "cart", fetch = FetchType.LAZY, cascade =
            {
                    CascadeType.REMOVE,
                    CascadeType.MERGE,
                    CascadeType.PERSIST
            }, orphanRemoval = true)
    private List<CartBook> books;

    @OneToOne
    @JoinColumn(name = "user_fk")
    private User user;

    public Cart() {
    }

    public void addBook(CartBook cartBook) {
        if (books == null) {
            books = new ArrayList<>();
        }
        if (!books.contains(cartBook))
            this.books.add(cartBook);
    }

    public void removeBook(CartBook cartBook){
        if (books.contains(cartBook))
            this.books.remove(cartBook);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cart r = (Cart) o;
        return Objects.equals(id, r.id);
    }

    public List<CartBook> getBooks() {
        return books;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
