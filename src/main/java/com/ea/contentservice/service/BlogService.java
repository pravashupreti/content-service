package com.ea.contentservice.service;

import com.ea.contentservice.model.Blog;
import com.ea.contentservice.payload.ApiResponse;
import com.ea.contentservice.payload.BlogRequest;
import com.ea.contentservice.payload.BlogResponse;
import com.ea.contentservice.payload.PagedResponse;

public interface BlogService {

    PagedResponse<Blog> getAllBlogs(int page, int size);

    PagedResponse<Blog> getBlogsByUserId(Long userId, int page, int size);

    PagedResponse<Blog> getBlogsByCategory(Long id, int page, int size);

    PagedResponse<Blog> getBlogsByTag(Long id, int page, int size);

    Blog updateBlog(Long id, BlogRequest newBlogRequest);

    ApiResponse deleteBlog(Long id);

    BlogResponse addBlog(BlogRequest blogRequest);

    Blog getBlog(Long id);

}
