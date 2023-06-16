package com.example.exam.folder.service;

public class BranchNotFoundException extends RuntimeException {
    public BranchNotFoundException(Long id) {
        super(String.format("Branch with id [%s] is not found", id));
    }
}