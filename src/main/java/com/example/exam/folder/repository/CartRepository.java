package com.example.exam.folder.repository;

import com.example.exam.folder.model.Cart;
import com.example.exam.folder.model.CartBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartRepository extends JpaRepository<Cart, Long> {
    @Query("Select bg from CartBook bg where bg.cart.id = :cartId and bg.book.id = :bookId")
    CartBook getCartBook(@Param("cartId") Long cartId, @Param("bookId") Long bookId);
}
