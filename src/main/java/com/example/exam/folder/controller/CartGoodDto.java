package com.example.exam.folder.controller;

public class CartGoodDto {
    private GoodDto good;
    private Long cartId;
    private Integer count;

    public CartGoodDto() {
    }

    public CartGoodDto(GoodDto good, Long cartId, Integer count) {
        this.good = good;
        this.cartId = cartId;
        this.count = count;
    }

    public void setGood(GoodDto good) {
        this.good = good;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public GoodDto getGood() {
        return good;
    }

    public Long getCartId() {
        return cartId;
    }

    public Integer getCount() {
        return count;
    }
}
