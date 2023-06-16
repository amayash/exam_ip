package com.example.exam.folder.service;

public class CommitNotFoundException extends RuntimeException {
    public CommitNotFoundException(Long id) {
        super(String.format("Commit with id [%s] is not found", id));
    }
}