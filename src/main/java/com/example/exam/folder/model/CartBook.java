package com.example.exam.folder.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Table(name = "cart_book")
public class CartBook {
    @EmbeddedId
    private CartBookKey id;
    @ManyToOne
    @MapsId("bookId")
    @JoinColumn(name = "book_id")
    private Book book;
    @ManyToOne
    @MapsId("cartId")
    @JoinColumn(name = "cart_id")
    private Cart cart;
    @Column(name = "count")
    private Integer count;
    @NotNull(message = "timestamp can't be null or empty")
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    protected LocalDateTime timestamp;

    public CartBook() {
    }

    public CartBook(Cart cart, Book book, Integer count, LocalDateTime timestamp) {
        this.cart = cart;
        this.book = book;
        this.count = count;
        this.timestamp = timestamp;
        this.id = new CartBookKey(book.getId(), cart.getId());
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public CartBookKey getId() {
        return id;
    }

    public void setId(CartBookKey id) {
        this.id = id;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
