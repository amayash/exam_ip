package com.example.exam.folder.service;

public class FileNotFoundException extends RuntimeException {
    public FileNotFoundException(Long id) {
        super(String.format("File with id [%s] is not found", id));
    }
}