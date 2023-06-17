package com.example.exam.folder.controller;

import com.example.exam.folder.model.UserRole;
import com.example.exam.folder.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/category")
public class CategoryMvcController {
    private final CategoryService categoryService;

    public CategoryMvcController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public String getCategories(Model model) {
        model.addAttribute("categories",
                categoryService.findAllCategories().stream()
                        .map(CategoryDto::new)
                        .toList());
        return "category";
    }

    @GetMapping(value = {"/edit", "/edit/{id}"})
    @Secured({UserRole.AsString.ADMIN})
    public String editCategory(@PathVariable(required = false) Long id,
                             Model model) {
        if (id == null || id <= 0) {
            model.addAttribute("categoryDto", new CategoryDto());
        } else {
            model.addAttribute("categoryId", id);
            model.addAttribute("categoryDto", new CategoryDto(categoryService.findCategory(id)));
        }
        return "category-edit";
    }

    @PostMapping(value = {"/", "/{id}"})
    @Secured({UserRole.AsString.ADMIN})
    public String saveCategory(@PathVariable(required = false) Long id,
                             @ModelAttribute @Valid CategoryDto categoryDto,
                             BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "category-edit";
        }
        if (id == null || id <= 0) {
            categoryService.addCategory(categoryDto.getName());
        } else {
            categoryService.updateCategory(id, categoryDto.getName());
        }
        return "redirect:/category";
    }

    @PostMapping("/delete/{id}")
    @Secured({UserRole.AsString.ADMIN})
    public String deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return "redirect:/category";
    }
}
