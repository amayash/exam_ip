package com.example.exam.folder.repository;

import com.example.exam.folder.model.Cart;
import com.example.exam.folder.model.CartGood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartRepository extends JpaRepository<Cart, Long> {
    @Query("Select bg from CartGood bg where bg.cart.id = :cartId and bg.good.id = :goodId")
    CartGood getCartGood(@Param("cartId") Long cartId, @Param("goodId") Long goodId);
}
