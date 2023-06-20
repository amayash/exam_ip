package com.example.exam.folder.service;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(Long id) {
        super(String.format("Book with id [%s] is not found", id));
    }
}