package com.example.exam.folder.controller;

import com.example.exam.folder.model.Author;
import com.example.exam.folder.model.Book;
import com.example.exam.folder.model.BookExtension;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class BookDto {
    private long id;
    private String name;
    private Double price;
    private CategoryDto category;
    private List<String> authors;
    private Long capacity;
    private int maxCount;

    public BookDto() {
    }

    public BookDto(BookExtension book) {
        this.id = book.getId();
        this.name = book.getName();
        this.capacity = book.getCapacity();
        this.maxCount = book.getMaxCount();
        if (book.getCategory() != null) {
            this.category = new CategoryDto(book.getCategory());
        } else this.category = null;
        if (book.getAuthors() != null) {
            authors = book.getAuthors().stream()
                    .map(Author::getName).toList();
        }
    }

    public BookDto(Book book) {
        this.id = book.getId();
        this.name = book.getName();
        this.maxCount = book.getMaxCount();
        if (book.getCategory() != null) {
            this.category = new CategoryDto(book.getCategory());
        } else this.category = null;
        if (book.getAuthors() != null) {
            authors = book.getAuthors().stream()
                    .map(Author::getName).toList();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public CategoryDto getCategory() {
        return category;
    }

    public void setCategory(CategoryDto category) {
        this.category = category;
    }

    public Long getCapacity() {
        return capacity;
    }

    public void setCapacity(Long capacity) {
        this.capacity = capacity;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }
}
