package com.ea.contentservice.service.impl;

import com.ea.contentservice.exception.ResourceNotFoundException;

import com.ea.contentservice.model.Category;

import com.ea.contentservice.payload.ApiResponse;
import com.ea.contentservice.payload.PagedResponse;
import com.ea.contentservice.repository.CategoryRepository;

import com.ea.contentservice.service.CategoryService;
import com.ea.contentservice.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public PagedResponse<Category> getAllCategories(int page, int size) {
        AppUtils.validatePageNumberAndSize(page, size);

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");

        Page<Category> categories = categoryRepository.findAll(pageable);

        List<Category> content = categories.getNumberOfElements() == 0 ? Collections.emptyList() : categories.getContent();

        return new PagedResponse<>(content, categories.getNumber(), categories.getSize(), categories.getTotalElements(),
                categories.getTotalPages(), categories.isLast());
    }

    @Override
    public ResponseEntity<Category> getCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Category> addCategory(Category category) {
        Category newCategory = categoryRepository.save(category);
        return new ResponseEntity<>(newCategory, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Category> updateCategory(Long id, Category newCategory) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));

        category.setName(newCategory.getName());
        Category updatedCategory = categoryRepository.save(category);
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);


    }

    @Override
    public ResponseEntity<ApiResponse> deleteCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("category", "id", id));

        categoryRepository.deleteById(id);
        return new ResponseEntity<>(new ApiResponse(Boolean.TRUE, "You successfully deleted category"), HttpStatus.OK);


    }
}






















