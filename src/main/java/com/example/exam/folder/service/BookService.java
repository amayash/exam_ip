package com.example.exam.folder.service;

import com.example.exam.folder.model.Author;
import com.example.exam.folder.model.Book;
import com.example.exam.folder.model.BookExtension;
import com.example.exam.folder.model.Category;
import com.example.exam.folder.repository.BookRepository;
import com.example.exam.util.validation.ValidatorUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final CategoryService categoryService;
    private final ValidatorUtil validatorUtil;

    public BookService(BookRepository bookRepository,
                       CategoryService categoryService, ValidatorUtil validatorUtil) {
        this.bookRepository = bookRepository;
        this.categoryService = categoryService;
        this.validatorUtil = validatorUtil;
    }

    @Transactional
    public Book addBook(String name, Long categoryId, Integer capacity) {
        final Book book = new Book(name, capacity);
        final Category category = categoryService.findCategory(categoryId);
        book.setCategory(category);
        validatorUtil.validate(book);
        return bookRepository.save(book);
    }

    @Transactional(readOnly = true)
    public BookExtension findBook(Long id) {
        return bookRepository.getBookWithCapacity(id)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    @Transactional
    public List<Book> findAllBooks(String search) {
        return bookRepository.findAll(search);
    }

    @Transactional
    public List<Book> findAllBooks(Integer search) {
        return bookRepository.findAll(search);
    }

    @Transactional
    public List<Book> findBooksByAuthor(Long id) {
        return bookRepository.findAllByAuthorsId(id);
    }

    @Transactional(readOnly = true)
    public Book findBaseBook(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public List<BookExtension> findAllBooks() {
        List<BookExtension> books = bookRepository.getBooksWithCapacity();
        return books;
    }

    @Transactional
    public Book updateBook(Long id, String name, Long categoryId) {
        final Book currentBook = findBaseBook(id);
        final Category category = categoryService.findCategory(categoryId);
        currentBook.setName(name);
        currentBook.setCategory(category);
        validatorUtil.validate(currentBook);
        return bookRepository.save(currentBook);
    }

    @Transactional
    public Book deleteBook(Long id) {
        final Book currentBook = findBaseBook(id);
        bookRepository.delete(currentBook);
        return currentBook;
    }

    @Transactional
    public void deleteAllBooks() {
        bookRepository.deleteAll();
    }

    @Transactional
    public Integer getCapacity(Long bookId) {
        return bookRepository.getCapacity(bookId).orElse(0);
    }
}
