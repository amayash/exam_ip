package com.example.exam.folder.model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CartBookKey implements Serializable {
    private Long bookId;
    private Long cartId;

    public CartBookKey() {
    }

    public CartBookKey(Long bookId, Long cartId) {
        this.bookId = bookId;
        this.cartId = cartId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CartBookKey that)) return false;
        return Objects.equals(getBookId(), that.getBookId()) && Objects.equals(getCartId(), that.getCartId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBookId(), getCartId());
    }
}
