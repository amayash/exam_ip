package com.example.exam.folder.model;

public class BookExtension extends Book {
    private Long capacity;
    public BookExtension(Book book, Long capacity) {
        super(book);
        this.capacity = capacity;
    }

    public Long getCapacity() {
        return capacity;
    }

    public void setCapacity(Long capacity) {
        this.capacity = capacity;
    }
}
