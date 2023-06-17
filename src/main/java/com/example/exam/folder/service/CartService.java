package com.example.exam.folder.service;

import com.example.exam.folder.model.Cart;
import com.example.exam.folder.model.CartGood;
import com.example.exam.folder.model.Good;
import com.example.exam.folder.repository.CartRepository;
import com.example.exam.util.validation.ValidatorUtil;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final GoodService goodService;
    private final ValidatorUtil validatorUtil;
    public CartService(CartRepository cartRepository, GoodService goodService, ValidatorUtil validatorUtil) {
        this.cartRepository = cartRepository;
        this.goodService = goodService;
        this.validatorUtil = validatorUtil;
    }

    @Transactional
    public Cart addCart() {
        final Cart cart = new Cart();
        validatorUtil.validate(cart);
        return cartRepository.save(cart);
    }

    @Transactional
    public Cart addGood(Long id, Long goodId, Integer count) {
        final Good currentGood = goodService.findGood(goodId);
        final Cart currentCart = findCart(id);
        final CartGood currentCartGood = cartRepository.getCartGood(id, goodId);

        final Integer currentGoodCapacity = currentGood.getMaxCount() - goodService.getCapacity(goodId);
        if (currentGoodCapacity < count ||
                (currentCartGood != null && currentCartGood.getCount() + count > currentGood.getMaxCount())) {
            throw new IllegalArgumentException(String.format("No more goods in good. Capacity: %1$s. Count: %2$s",
                    currentGoodCapacity, count));
        }

        if (currentCartGood == null) {
            currentCart.addGood(new CartGood(currentCart, currentGood, count));
        }
        else if (currentCartGood.getCount() + count <= currentGood.getMaxCount()) {
            currentCart.removeGood(currentCartGood);
            currentCart.addGood(new CartGood(currentCart, currentGood,
                    currentCartGood.getCount() + count));
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
    public Cart deleteGoodInCart(Long id, Long good, Integer count) {
        final Cart currentCart = findCart(id);
        final Good currentGood = goodService.findGood(good);
        final CartGood currentCartGood = cartRepository.getCartGood(id, good);
        if (currentCartGood == null)
            throw new EntityNotFoundException();

        if (count >= currentCartGood.getCount()) {
            currentCart.removeGood(currentCartGood);
        }
        else {
            currentCart.removeGood(currentCartGood);
            currentCart.addGood(new CartGood(currentCart, currentGood,
                    currentCartGood.getCount() - count));
        }
        return cartRepository.save(currentCart);
    }

    @Transactional
    public void deleteAllCarts() {
        cartRepository.deleteAll();
    }
}
