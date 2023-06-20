package com.example.exam.folder.repository;

import com.example.exam.folder.model.Author;
import com.example.exam.folder.model.Book;
import com.example.exam.folder.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
