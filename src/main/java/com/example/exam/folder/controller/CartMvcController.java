package com.example.exam.folder.controller;

import com.example.exam.folder.model.Cart;
import com.example.exam.folder.model.User;
import com.example.exam.folder.model.UserRole;
import com.example.exam.folder.service.CartService;
import com.example.exam.folder.service.GoodService;
import com.example.exam.folder.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/cart")
public class CartMvcController {
    private final CartService cartService;
    private final UserService userService;
    private final GoodService goodService;

    public CartMvcController(CartService cartService, UserService userService, GoodService goodService) {
        this.cartService = cartService;
        this.userService = userService;
        this.goodService = goodService;
    }

    @GetMapping(value = {"/edit"})
    public String editCart(Model model,
                           Principal principal) {
        User user = userService.findByLogin(principal.getName());
        Cart cart = user.getCart();
        List<CartGoodDto> temp = cart.getGoods()
                .stream().map(x -> new CartGoodDto(new GoodDto(x.getGood()),
                        x.getCart().getId(), x.getCount())).toList();
        List<GoodDto> goods = goodService.findAllGoods().stream()
                .map(GoodDto::new)
                .toList();
        HashMap<GoodDto, Integer> cartGoods = new HashMap<>();
        for (var os : temp) {
            cartGoods.put(os.getGood(), os.getCount());
        }
        model.addAttribute("cartGoods", cartGoods);
        model.addAttribute("goods", goods);
        model.addAttribute("cartGoodDto", new CartGoodDto());

        return "cartgood";
    }

    @PostMapping(value = {""})
    public String editCart(@RequestParam("good") Long good,
                           @RequestParam(value = "count", required = false) Integer count,
                           Model model,
                           Principal principal) {
        User user = userService.findByLogin(principal.getName());
        Cart cart = cartService.findCart(user.getCart().getId());
        if (count == null)
            cartService.deleteGoodInCart(cart.getId(), good, Integer.MAX_VALUE);
        else if (count > 0)
            cartService.addGood(cart.getId(), good, count);
        return "redirect:/cart/edit";
    }
}