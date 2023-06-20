package com.example.exam.folder.controller;

import com.example.exam.folder.model.Cart;
import com.example.exam.folder.model.User;
import com.example.exam.folder.model.UserRole;
import com.example.exam.folder.service.CartService;
import com.example.exam.folder.service.BookService;
import com.example.exam.folder.service.UserService;
import groovy.lang.Tuple2;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/cart")
public class CartMvcController {
    private final CartService cartService;
    private final UserService userService;
    private final BookService bookService;

    public CartMvcController(CartService cartService, UserService userService, BookService bookService) {
        this.cartService = cartService;
        this.userService = userService;
        this.bookService = bookService;
    }

    @GetMapping(value = {"/edit/{id}", "/edit"})
    public String editCart(Model model,
                           Principal principal,
                           @PathVariable(required = false) Long id) {
        User user = userService.findByLogin(principal.getName());
        if (id == null && user.getRole() == UserRole.ADMIN)
            return "redirect:/cart/edit/"+id;
        if (id != null && user.getRole() == UserRole.ADMIN) {
            user = userService.findUser(id);
        }
        if (user.getRole() == UserRole.ADMIN) return "redirect:/user";
        Cart cart = user.getCart();
        List<CartBookDto> temp = cart.getBooks()
                .stream().map(x -> new CartBookDto(new BookDto(x.getBook()),
                        x.getCart().getId(), x.getCount(), x.getTimestamp())).toList();
        List<BookDto> books = bookService.findAllBooks().stream()
                .map(BookDto::new)
                .toList();

        model.addAttribute("cartBooks", temp);
        model.addAttribute("books", books);
        model.addAttribute("cartBookDto", new CartBookDto());

        return "cartbook";
    }

    @PostMapping(value = {"/{id}","/"})
    public String editCart(@PathVariable(required = false) Long id,
                           @RequestParam("book") Long book,
                           @RequestParam(value = "count", required = false) Integer count,
                           Model model,
                           Principal principal) {
        User user = userService.findByLogin(principal.getName());
        if (id != null && user.getRole() == UserRole.ADMIN) {
            user = userService.findUser(id);
        }
        Cart cart = user.getCart();
        if (count == null)
            cartService.deleteBookInCart(cart.getId(), book, Integer.MAX_VALUE);
        else if (count > 0)
            cartService.addBook(cart.getId(), book, count);
        return "redirect:/cart/edit/"+id;
    }
}