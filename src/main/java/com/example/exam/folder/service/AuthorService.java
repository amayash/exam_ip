package com.example.exam.folder.service;

import com.example.exam.folder.model.Author;
import com.example.exam.folder.model.Book;
import com.example.exam.folder.repository.AuthorRepository;
import com.example.exam.util.validation.ValidatorUtil;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final BookService bookService;
    private final ValidatorUtil validatorUtil;

    public AuthorService(AuthorRepository authorRepository, BookService bookService, ValidatorUtil validatorUtil) {
        this.authorRepository = authorRepository;
        this.bookService = bookService;
        this.validatorUtil = validatorUtil;
    }

    @Transactional
    public Author addAuthor(String name) {
        final Author author = new Author(name);
        validatorUtil.validate(author);
        return authorRepository.save(author);
    }

    @Transactional
    public Author addBook(Long id, Long bookId) {
        Author e = findAuthor(id);
        Book book = bookService.findBaseBook(bookId);
        e.setBook(book);
        return authorRepository.save(e);
    }

    @Transactional
    public Author deleteBook(Long id, Long bookId) {
        Author e = findAuthor(id);
        Book book = bookService.findBaseBook(bookId);
        if (book.getAuthors().size() == 1) {
            bookService.deleteBook(bookId);
            return e;
        }
        e.removeBook(book);
        return authorRepository.save(e);
    }

    @Transactional
    public Author findAuthor(Long id) {
        final Optional<Author> author = authorRepository.findById(id);
        return author.orElseThrow(() -> new EntityNotFoundException("Entity author was not found with id " + id));
    }

    @Transactional
    public List<Author> findAllAuthors() {
        return authorRepository.findAll();
    }

    @Transactional
    public Author updateAuthor(Long id, String name) {
        final Author currentAuthor = findAuthor(id);
        currentAuthor.setName(name);
        validatorUtil.validate(currentAuthor);
        return authorRepository.save(currentAuthor);
    }

    @Transactional
    public Author deleteAuthor(Long id) {
        final Author currentAuthor = findAuthor(id);
        for (var book : currentAuthor.getBooks()) {
            if (book.getAuthors().size() == 1)
                bookService.deleteBook(book.getId());
        }
        authorRepository.delete(currentAuthor);
        return currentAuthor;
    }

    @Transactional
    public void deleteAllAuthors() {
        authorRepository.deleteAll();
    }
}
