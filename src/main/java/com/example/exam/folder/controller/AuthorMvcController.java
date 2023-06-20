package com.example.exam.folder.controller;

import com.example.exam.folder.model.Author;
import com.example.exam.folder.model.User;
import com.example.exam.folder.model.UserRole;
import com.example.exam.folder.service.AuthorService;
import com.example.exam.folder.service.BookService;
import com.example.exam.folder.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/author")
public class AuthorMvcController {
    private final AuthorService authorService;
    private final BookService bookService;

    public AuthorMvcController(AuthorService authorService, BookService bookService) {
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @GetMapping
    public String getAuthors(Model model) {
        model.addAttribute("authors", authorService.findAllAuthors().stream()
                .map(AuthorDto::new)
                .toList());
        return "author";
    }

    @GetMapping("/{id}")
    public String getBooks(@PathVariable Long id, Model model) {
        List<BookDto> books = bookService.findAllBooks().stream().map(BookDto::new).toList();
        model.addAttribute("booksForSelect", books);
        model.addAttribute("books", bookService.findBooksByAuthor(id).stream()
                .map(BookDto::new)
                .toList());
        model.addAttribute("bookDto", new BookDto());
        return "author-one";
    }

    @PostMapping(value = "/{id}/add")
    @Secured({UserRole.AsString.ADMIN})
    public String addBook(@PathVariable Long id,
                          @RequestParam Long bookId) {
        authorService.addBook(id, bookId);
        return "redirect:/author/" + id;
    }

    @PostMapping(value = "/{id}/remove/{bookId}")
    @Secured({UserRole.AsString.ADMIN})
    public String removeBook(@PathVariable Long id,
                             @PathVariable Long bookId) {
        authorService.deleteBook(id, bookId);
        return "redirect:/author/" + id;
    }

    @GetMapping(value = {"/edit", "/edit/{id}"})
    @Secured({UserRole.AsString.ADMIN})
    public String editAuthor(@PathVariable(required = false) Long id,
                               Model model) {
        if (id == null || id <= 0) {
            List<BookDto> books = bookService.findAllBooks().stream()
                    .map(BookDto::new)
                    .toList();
            model.addAttribute("books", books);
            model.addAttribute("authorDto", new AuthorDto());
        } else {
            model.addAttribute("authorDto", new AuthorDto(authorService.findAuthor(id)));
        }
        return "author-edit";
    }

    @PostMapping(value = "/{id}")
    @Secured({UserRole.AsString.ADMIN})
    public String editAuthor(@PathVariable Long id,
                               @ModelAttribute @Valid AuthorDto authorDto,
                               BindingResult bindingResult,
                               Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "author-edit";
        }
        authorDto.setId(id);
        authorService.updateAuthor(id, authorDto.getName());

        return "redirect:/author";
    }

    @PostMapping(value = "/")
    @Secured({UserRole.AsString.ADMIN})
    public String saveAuthor(@ModelAttribute @Valid AuthorDto authorDto) {
        authorService.addAuthor(authorDto.getName());
        return "redirect:/author";
    }

    @PostMapping("/delete/{id}")
    @Secured({UserRole.AsString.ADMIN})
    public String deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return "redirect:/author";
    }
}
