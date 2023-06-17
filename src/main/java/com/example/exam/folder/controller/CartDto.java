package com.example.exam.folder.controller;

import com.example.exam.folder.model.Cart;

import java.sql.Date;
import java.util.List;

public class CartDto {
    private long id;
    private List<CartGoodDto> goods;

    public CartDto() {
    }

    public CartDto(Cart cart) {
        this.id = cart.getId();
        if (cart.getGoods() != null && cart.getGoods().size() > 0)
            this.goods = cart.getGoods()
                    .stream()
                    .map(x -> new CartGoodDto(new GoodDto(x.getGood()),
                            x.getId().getCartId(), x.getCount())).toList();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<CartGoodDto> getGoods() {
        return goods;
    }

    public void setGoods(List<CartGoodDto> goods) {
        this.goods = goods;
    }
}
