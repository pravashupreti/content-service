package com.ea.contentservice.service.impl;

import com.ea.contentservice.exception.BadRequestException;
import com.ea.contentservice.exception.ResourceNotFoundException;
import com.ea.contentservice.model.Blog;
import com.ea.contentservice.model.Category;
import com.ea.contentservice.model.Tag;

import com.ea.contentservice.payload.ApiResponse;
import com.ea.contentservice.payload.BlogRequest;
import com.ea.contentservice.payload.BlogResponse;
import com.ea.contentservice.payload.PagedResponse;
import com.ea.contentservice.repository.BlogRepository;
import com.ea.contentservice.repository.CategoryRepository;
import com.ea.contentservice.repository.TagRepository;

import com.ea.contentservice.service.BlogService;
import com.ea.contentservice.utils.AppConstants;
import com.ea.contentservice.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.ea.contentservice.utils.AppConstants.*;

@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogRepository blogRepository;


    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TagRepository tagRepository;

    @Override
    public PagedResponse<Blog> getAllBlogs(int page, int size) {
        validatePageNumberAndSize(page, size);

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, ID);

        Page<Blog> blogs = blogRepository.findAll(pageable);

        List<Blog> content = blogs.getNumberOfElements() == 0 ? Collections.emptyList() : blogs.getContent();

        return new PagedResponse<>(content, blogs.getNumber(), blogs.getSize(), blogs.getTotalElements(),
                blogs.getTotalPages(), blogs.isLast());
    }

    @Override
    public PagedResponse<Blog> getBlogsByUserId(Long userId, int page, int size) {
        validatePageNumberAndSize(page, size);

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, ID);
        Page<Blog> blogs = blogRepository.findByUserId(userId, pageable);

        List<Blog> content = blogs.getNumberOfElements() == 0 ? Collections.emptyList() : blogs.getContent();

        return new PagedResponse<>(content, blogs.getNumber(), blogs.getSize(), blogs.getTotalElements(),
                blogs.getTotalPages(), blogs.isLast());
    }

    @Override
    public PagedResponse<Blog> getBlogsByCategory(Long id, int page, int size) {
        AppUtils.validatePageNumberAndSize(page, size);
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(CATEGORY, ID, id));

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, ID);
        Page<Blog> blogs = blogRepository.findByCategory(category, pageable);

        List<Blog> content = blogs.getNumberOfElements() == 0 ? Collections.emptyList() : blogs.getContent();

        return new PagedResponse<>(content, blogs.getNumber(), blogs.getSize(), blogs.getTotalElements(),
                blogs.getTotalPages(), blogs.isLast());
    }

    @Override
    public PagedResponse<Blog> getBlogsByTag(Long id, int page, int size) {
        AppUtils.validatePageNumberAndSize(page, size);

        Tag tag = tagRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(TAG, ID, id));

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, ID);

        Page<Blog> blogs = blogRepository.findByTagsIn(Collections.singletonList(tag), pageable);

        List<Blog> content = blogs.getNumberOfElements() == 0 ? Collections.emptyList() : blogs.getContent();

        return new PagedResponse<>(content, blogs.getNumber(), blogs.getSize(), blogs.getTotalElements(),
                blogs.getTotalPages(), blogs.isLast());
    }

    @Override
    public Blog updateBlog(Long id, BlogRequest newBlogRequest) {
        Blog blog = blogRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(BLOG, ID, id));
        Category category = categoryRepository.findById(newBlogRequest.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException(CATEGORY, ID, newBlogRequest.getCategoryId()));

        blog.setTitle(newBlogRequest.getTitle());
        blog.setBody(newBlogRequest.getBody());
        blog.setCategory(category);
        return blogRepository.save(blog);

    }

    @Override
    public ApiResponse deleteBlog(Long id) {
        Blog blog = blogRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(BLOG, ID, id));

        blogRepository.deleteById(id);
        return new ApiResponse(Boolean.TRUE, "You successfully deleted blog");


    }

    @Override
    public BlogResponse addBlog(BlogRequest blogRequest ) {
        Category category = categoryRepository.findById(blogRequest.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException(CATEGORY, ID, blogRequest.getCategoryId()));

        List<Tag> tags = new ArrayList<>(blogRequest.getTags().size());

        for (String name : blogRequest.getTags()) {
            Tag tag = tagRepository.findByName(name);
            tag = tag == null ? tagRepository.save(new Tag(name)) : tag;

            tags.add(tag);
        }

        Blog blog = new Blog();
        blog.setBody(blogRequest.getBody());
        blog.setTitle(blogRequest.getTitle());
        blog.setCategory(category);
        blog.setUserId(blogRequest.getUserId());
        blog.setTags(tags);

        Blog newBlog = blogRepository.save(blog);

        BlogResponse blogResponse = new BlogResponse();

        blogResponse.setId(newBlog.getId());

        blogResponse.setTitle(newBlog.getTitle());
        blogResponse.setBody(newBlog.getBody());
        blogResponse.setCategory(newBlog.getCategory().getName());

        List<String> tagNames = new ArrayList<>(newBlog.getTags().size());

        for (Tag tag : newBlog.getTags()) {
            tagNames.add(tag.getName());
        }

        blogResponse.setTags(tagNames);

        return blogResponse;
    }

    @Override
    public Blog getBlog(Long id) {
        return blogRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(BLOG, ID, id));
    }

    private void validatePageNumberAndSize(int page, int size) {
        if (page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }

        if (size < 0) {
            throw new BadRequestException("Size number cannot be less than zero.");
        }

        if (size > AppConstants.MAX_PAGE_SIZE) {
            throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
        }
    }
}
