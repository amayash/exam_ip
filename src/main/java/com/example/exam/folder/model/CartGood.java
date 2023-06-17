package com.example.exam.folder.model;

import jakarta.persistence.*;
@Entity
@Table(name = "cart_good")
public class CartGood {
    @EmbeddedId
    private CartGoodKey id;
    @ManyToOne
    @MapsId("goodId")
    @JoinColumn(name = "good_id")
    private Good good;
    @ManyToOne
    @MapsId("cartId")
    @JoinColumn(name = "cart_id")
    private Cart cart;
    @Column(name = "count")
    private Integer count;

    public CartGood() {
    }

    public CartGood(Cart cart, Good good, Integer count) {
        this.cart = cart;
        this.good = good;
        this.count = count;
        this.id = new CartGoodKey(good.getId(), cart.getId());
    }

    public CartGoodKey getId() {
        return id;
    }

    public void setId(CartGoodKey id) {
        this.id = id;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Good getGood() {
        return good;
    }

    public void setGood(Good good) {
        this.good = good;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
