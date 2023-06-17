package com.example.exam.folder.model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CartGoodKey implements Serializable {
    private Long goodId;
    private Long cartId;

    public CartGoodKey() {
    }

    public CartGoodKey(Long goodId, Long cartId) {
        this.goodId = goodId;
        this.cartId = cartId;
    }

    public Long getGoodId() {
        return goodId;
    }

    public void setGoodId(Long goodId) {
        this.goodId = goodId;
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
        if (!(o instanceof CartGoodKey that)) return false;
        return Objects.equals(getGoodId(), that.getGoodId()) && Objects.equals(getCartId(), that.getCartId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getGoodId(), getCartId());
    }
}
