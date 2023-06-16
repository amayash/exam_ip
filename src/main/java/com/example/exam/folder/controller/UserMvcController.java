package com.example.exam.folder.controller;

import com.example.exam.folder.model.UserRole;
import com.example.exam.folder.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/user")
@Secured({UserRole.AsString.ADMIN})
public class UserMvcController {
    private final UserService userService;

    public UserMvcController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getUsers(@RequestParam(defaultValue = "1") int page,
                           @RequestParam(defaultValue = "5") int size,
                           Model model) {
        final Page<UserDto> users = userService.findAllPages(page, size)
                .map(UserDto::new);
        model.addAttribute("users", users);
        final int totalPages = users.getTotalPages();
        final List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                .boxed()
                .toList();
        model.addAttribute("pages", pageNumbers);
        model.addAttribute("totalPages", totalPages);
        return "user";
    }

    @GetMapping(value = {"/edit", "/edit/{id}"})
    public String editUser(@PathVariable(required = false) Long id,
                           Model model) {
        if (id == null || id <= 0) {
            model.addAttribute("userDto", new UserDto());
        } else {
            model.addAttribute("userId", id);
            model.addAttribute("userDto", new UserDto(userService.findUser(id)));
        }
        return "user-edit";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/user";
    }

    @PostMapping("/{id}")
    @Secured({UserRole.AsString.ADMIN})
    public String updateUser(@PathVariable Long id,
                                 @ModelAttribute @Valid UserDto userDto,
                                 BindingResult bindingResult,
                                 Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "user-edit";
        }
        userService.updateUser(id, userDto.getLogin(), userDto.getPassword());
        return "redirect:/user";
    }
}
