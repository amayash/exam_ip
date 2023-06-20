package com.example.exam.folder.controller;

import com.example.exam.folder.model.Author;

import java.util.List;

public class AuthorDto {
    private long id;
    private String name;
    private List<BookDto> books;

    public AuthorDto() {
    }

    public AuthorDto(Author author) {
        this.id = author.getId();
        this.name = author.getName();
        this.books = author.getBooks().stream().map(BookDto::new).toList();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
