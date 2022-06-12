package com.ea.contentservice.controller;


import com.ea.contentservice.model.Category;
import com.ea.contentservice.payload.ApiResponse;
import com.ea.contentservice.payload.PagedResponse;

import com.ea.contentservice.service.CategoryService;
import com.ea.contentservice.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public PagedResponse<Category> getAllCategories(
            @RequestParam(name = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
            @RequestParam(name = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size) {
        return categoryService.getAllCategories(page, size);
    }

    @PostMapping

    public ResponseEntity<Category> addCategory(@Valid @RequestBody Category category) {

        return categoryService.addCategory(category);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategory(@PathVariable(name = "id") Long id) {
        return categoryService.getCategory(id);
    }

    @PutMapping("/{id}")

    public ResponseEntity<Category> updateCategory(@PathVariable(name = "id") Long id, @Valid @RequestBody Category category) {
        return categoryService.updateCategory(id, category);
    }

    @DeleteMapping("/{id}")

    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable(name = "id") Long id) {
        return categoryService.deleteCategory(id);
    }

}
