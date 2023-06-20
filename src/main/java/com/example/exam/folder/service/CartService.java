package com.example.exam.folder.service;

import com.example.exam.folder.model.Cart;
import com.example.exam.folder.model.CartBook;
import com.example.exam.folder.model.Book;
import com.example.exam.folder.repository.CartRepository;
import com.example.exam.util.validation.ValidatorUtil;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final BookService bookService;
    private final ValidatorUtil validatorUtil;
    public CartService(CartRepository cartRepository, BookService bookService, ValidatorUtil validatorUtil) {
        this.cartRepository = cartRepository;
        this.bookService = bookService;
        this.validatorUtil = validatorUtil;
    }

    @Transactional
    public Cart addCart() {
        final Cart cart = new Cart();
        validatorUtil.validate(cart);
        return cartRepository.save(cart);
    }

    @Transactional
    public Cart addBook(Long id, Long bookId, Integer count) {
        final Book currentBook = bookService.findBook(bookId);
        final Cart currentCart = findCart(id);
        final CartBook currentCartBook = cartRepository.getCartBook(id, bookId);

        final Integer currentBookCapacity = currentBook.getMaxCount() - bookService.getCapacity(bookId);
        if (currentBookCapacity < count ||
                (currentCartBook != null && currentCartBook.getCount() + count > currentBook.getMaxCount())) {
            throw new IllegalArgumentException(String.format("No more books in book. Capacity: %1$s. Count: %2$s",
                    currentBookCapacity, count));
        }

        if (currentCartBook == null) {
            currentCart.addBook(new CartBook(currentCart, currentBook, count, LocalDateTime.now()));
        }
        else if (currentCartBook.getCount() + count <= currentBook.getMaxCount()) {
            currentCart.removeBook(currentCartBook);
            currentCart.addBook(new CartBook(currentCart, currentBook,
                    currentCartBook.getCount() + count, currentCartBook.getTimestamp()));
        }

        return cartRepository.save(currentCart);
    }

    @Transactional(readOnly = true)
    public Cart findCart(Long id) {
        final Optional<Cart> cart = cartRepository.findById(id);
        return cart.orElseThrow(() -> new CartNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public List<Cart> findAllCarts() {
        return cartRepository.findAll();
    }

    @Transactional
    public Cart deleteCart(Long id) {
        final Cart currentCart = findCart(id);
        cartRepository.delete(currentCart);
        return currentCart;
    }

    @Transactional
    public Cart deleteBookInCart(Long id, Long book, Integer count) {
        final Cart currentCart = findCart(id);
        final Book currentBook = bookService.findBook(book);
        final CartBook currentCartBook = cartRepository.getCartBook(id, book);
        if (currentCartBook == null)
            throw new EntityNotFoundException();

        if (count >= currentCartBook.getCount()) {
            currentCart.removeBook(currentCartBook);
        }
        else {
            currentCart.removeBook(currentCartBook);
            currentCart.addBook(new CartBook(currentCart, currentBook,
                    currentCartBook.getCount() - count, currentCartBook.getTimestamp()));
        }
        return cartRepository.save(currentCart);
    }

    @Transactional
    public void deleteAllCarts() {
        cartRepository.deleteAll();
    }
}
