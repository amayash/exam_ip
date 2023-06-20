package com.example.exam.folder.controller;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class CartBookDto {
    private BookDto book;
    private Long cartId;
    private Integer count;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime timestamp;
    public CartBookDto() {
    }

    public CartBookDto(BookDto book, Long cartId, Integer count, LocalDateTime timestamp) {
        this.book = book;
        this.cartId = cartId;
        this.count = count;
        this.timestamp = timestamp;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setBook(BookDto book) {
        this.book = book;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public BookDto getBook() {
        return book;
    }

    public Long getCartId() {
        return cartId;
    }

    public Integer getCount() {
        return count;
    }
}
