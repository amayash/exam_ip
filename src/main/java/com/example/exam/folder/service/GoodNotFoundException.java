package com.example.exam.folder.service;

public class GoodNotFoundException extends RuntimeException {
    public GoodNotFoundException(Long id) {
        super(String.format("Good with id [%s] is not found", id));
    }
}