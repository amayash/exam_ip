package com.example.exam.folder.service;

import com.example.exam.folder.model.Category;
import com.example.exam.folder.repository.CategoryRepository;
import com.example.exam.util.validation.ValidatorUtil;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ValidatorUtil validatorUtil;

    public CategoryService(CategoryRepository categoryRepository, ValidatorUtil validatorUtil) {
        this.categoryRepository = categoryRepository;
        this.validatorUtil = validatorUtil;
    }

    @Transactional
    public Category addCategory(String name) {
        final Category category = new Category(name);
        validatorUtil.validate(category);
        return categoryRepository.save(category);
    }

    @Transactional
    public Category findCategory(Long id) {
        final Optional<Category> category = categoryRepository.findById(id);
        return category.orElseThrow(() -> new CategoryNotFoundException(id));
    }

    @Transactional
    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    @Transactional
    public Category updateCategory(Long id, String name) {
        final Category currentCategory = findCategory(id);
        currentCategory.setName(name);
        validatorUtil.validate(currentCategory);
        return categoryRepository.save(currentCategory);
    }

    @Transactional
    public Category deleteCategory(Long id) {
        final Category currentCategory = findCategory(id);
        categoryRepository.delete(currentCategory);
        return currentCategory;
    }

    @Transactional
    public void deleteAllCategories() {
        categoryRepository.deleteAll();
    }
}
