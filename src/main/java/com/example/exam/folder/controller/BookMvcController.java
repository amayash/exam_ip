package com.example.exam.folder.controller;

import com.example.exam.folder.model.Book;
import com.example.exam.folder.model.UserRole;
import com.example.exam.folder.service.AuthorService;
import com.example.exam.folder.service.CategoryService;
import com.example.exam.folder.service.BookService;
import com.example.exam.folder.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/book")
public class BookMvcController {
    private final BookService bookService;
    private final AuthorService authorService;
    private final CategoryService categoryService;

    public BookMvcController(BookService bookService, AuthorService authorService, CategoryService categoryService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String getBooks(Model model) {
        List<BookDto> books = bookService.findAllBooks().stream()
                .map(BookDto::new)
                .toList();
        model.addAttribute("books", books);
        List<CategoryDto> categories = categoryService.findAllCategories().stream()
                .map(CategoryDto::new)
                .toList();
        model.addAttribute("categories", categories);
        return "book";
    }

    @GetMapping(value = "/search")
    public String searchBook(@RequestParam String request,
                             Model model) {
        List<BookDto> books = bookService.findAllBooks(request)
                .stream().map(BookDto::new).toList();
        model.addAttribute("books", books);
        return "book";
    }

    @GetMapping(value = "/search/category")
    public String searchBookByCategory(@RequestParam String request,
                                       Model model) {
        List<BookDto> books = bookService.findAllBooks(Integer.parseInt(request))
                .stream().map(BookDto::new).toList();
        model.addAttribute("books", books);
        return "book";
    }

    @GetMapping(value = {"/edit", "/edit/{id}"})
    @Secured({UserRole.AsString.ADMIN})
    public String editBook(@PathVariable(required = false) Long id,
                           Model model) {
        List<CategoryDto> categories = categoryService.findAllCategories().stream()
                .map(CategoryDto::new)
                .toList();
        model.addAttribute("categories", categories);
        if (id == null || id <= 0) {
            List<AuthorDto> authors = authorService.findAllAuthors().stream()
                    .map(AuthorDto::new)
                    .toList();
            model.addAttribute("bookDto", new BookDto());
            model.addAttribute("categories", categories);
            model.addAttribute("authors", authors);
        } else {
            model.addAttribute("bookId", id);
            model.addAttribute("bookDto", new BookDto(bookService.findBook(id)));
        }
        return "book-edit";
    }

    @PostMapping(value = "/{id}")
    @Secured({UserRole.AsString.ADMIN})
    public String editBook(@PathVariable Long id,
                           @ModelAttribute @Valid BookDto bookDto,
                           @RequestParam("categoryid") Long categoryId,
                           BindingResult bindingResult,
                           Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "book-edit";
        }
        bookService.updateBook(id, bookDto.getName(), categoryId);

        return "redirect:/book";
    }

    @PostMapping(value = "/")
    @Secured({UserRole.AsString.ADMIN})
    public String saveBook(@RequestParam("name") String name,
                           @RequestParam("categoryid") Long categoryId,
                           @RequestParam("maxCount") Integer capacity,
                           @RequestParam("authors") Long[] authors) {
        if (name.isBlank() || capacity <= 0 || authors.length == 0)
            throw new IllegalArgumentException();
        Book book = bookService.addBook(name, categoryId, capacity);
        for (var author : authors) {
            authorService.addBook(author, book.getId());
        }
        return "redirect:/book";
    }

    @PostMapping("/delete/{id}")
    @Secured({UserRole.AsString.ADMIN})
    public String deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return "redirect:/book";
    }
}
