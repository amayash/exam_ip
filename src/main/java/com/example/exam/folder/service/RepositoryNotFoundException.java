package com.example.exam.folder.service;

public class RepositoryNotFoundException extends RuntimeException {
    public RepositoryNotFoundException(Long id) {
        super(String.format("Repository with id [%s] is not found", id));
    }
}