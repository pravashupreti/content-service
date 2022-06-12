package com.ea.contentservice.service;

import com.ea.contentservice.model.Category;
import com.ea.contentservice.payload.ApiResponse;
import com.ea.contentservice.payload.PagedResponse;
import org.springframework.http.ResponseEntity;

public interface CategoryService {

    PagedResponse<Category> getAllCategories(int page, int size);

    ResponseEntity<Category> getCategory(Long id);

    ResponseEntity<Category> addCategory(Category category);

    ResponseEntity<Category> updateCategory(Long id, Category newCategory);

    ResponseEntity<ApiResponse> deleteCategory(Long id);

}
