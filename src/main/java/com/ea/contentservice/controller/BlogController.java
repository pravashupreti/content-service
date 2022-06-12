package com.ea.contentservice.controller;

import com.ea.contentservice.model.Blog;
import com.ea.contentservice.payload.ApiResponse;
import com.ea.contentservice.payload.BlogRequest;
import com.ea.contentservice.payload.BlogResponse;
import com.ea.contentservice.payload.PagedResponse;

import com.ea.contentservice.service.BlogService;
import com.ea.contentservice.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/blogs")
public class BlogController {
    @Autowired
    private BlogService blogService;

    @GetMapping
    public ResponseEntity<PagedResponse<Blog>> getAllBlogs(
            @RequestParam(value = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
            @RequestParam(value = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size) {
        PagedResponse<Blog> response = blogService.getAllBlogs(page, size);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<PagedResponse<Blog>> getBlogsByCategory(
            @RequestParam(value = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
            @RequestParam(value = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size,
            @PathVariable(name = "id") Long id) {
        PagedResponse<Blog> response = blogService.getBlogsByCategory(id, page, size);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/tag/{id}")
    public ResponseEntity<PagedResponse<Blog>> getBlogsByTag(
            @RequestParam(value = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
            @RequestParam(value = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size,
            @PathVariable(name = "id") Long id) {
        PagedResponse<Blog> response = blogService.getBlogsByTag(id, page, size);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping

    public ResponseEntity<BlogResponse> addBlog(@Valid @RequestBody BlogRequest blogRequest) {
        BlogResponse blogResponse = blogService.addBlog(blogRequest);

        return new ResponseEntity<>(blogResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Blog> getBlog(@PathVariable(name = "id") Long id) {
        Blog blog = blogService.getBlog(id);

        return new ResponseEntity<>(blog, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Blog> updateBlog(@PathVariable(name = "id") Long id,
                                           @Valid @RequestBody BlogRequest newBlogRequest
    ) {
        Blog blog = blogService.updateBlog(id, newBlogRequest);

        return new ResponseEntity<>(blog, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")

    public ResponseEntity<ApiResponse> deleteBlog(@PathVariable(name = "id") Long id) {
        ApiResponse apiResponse = blogService.deleteBlog(id);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
