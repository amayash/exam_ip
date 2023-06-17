package com.example.exam.folder.service;

public class CartNotFoundException extends RuntimeException {
    public CartNotFoundException(Long id) {
        super(String.format("Cart with id [%s] is not found", id));
    }
}
