package com.example.exam.folder.controller;

import com.example.exam.folder.model.Cart;

import java.sql.Date;
import java.util.List;

public class CartDto {
    private long id;
    private List<CartBookDto> books;

    public CartDto() {
    }

    public CartDto(Cart cart) {
        this.id = cart.getId();
        if (cart.getBooks() != null && cart.getBooks().size() > 0)
            this.books = cart.getBooks()
                    .stream()
                    .map(x -> new CartBookDto(new BookDto(x.getBook()),
                            x.getId().getCartId(), x.getCount(), x.getTimestamp())).toList();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<CartBookDto> getBooks() {
        return books;
    }

    public void setBooks(List<CartBookDto> books) {
        this.books = books;
    }
}
