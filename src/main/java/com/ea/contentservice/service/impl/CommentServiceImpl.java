package com.ea.contentservice.service.impl;

import com.ea.contentservice.exception.BlogapiException;
import com.ea.contentservice.exception.ResourceNotFoundException;
import com.ea.contentservice.model.Blog;
import com.ea.contentservice.model.Comment;

import com.ea.contentservice.payload.ApiResponse;
import com.ea.contentservice.payload.CommentRequest;
import com.ea.contentservice.payload.PagedResponse;
import com.ea.contentservice.repository.BlogRepository;
import com.ea.contentservice.repository.CommentRepository;

import com.ea.contentservice.service.CommentService;
import com.ea.contentservice.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;

import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
    private static final String THIS_COMMENT = " this comment";

    private static final String YOU_DON_T_HAVE_PERMISSION_TO = "You don't have permission to ";

    private static final String ID_STR = "id";

    private static final String COMMENT_STR = "Comment";

    private static final String BLOG_STR = "Blog";

    private static final String COMMENT_DOES_NOT_BELONG_TO_BLOG = "Comment does not belong to blog";

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private BlogRepository blogRepository;


    @Override
    public PagedResponse<Comment> getAllComments(Long blogId, int page, int size) {
        AppUtils.validatePageNumberAndSize(page, size);
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");

        Page<Comment> comments = commentRepository.findByBlogId(blogId, pageable);

        return new PagedResponse<>(comments.getContent(), comments.getNumber(), comments.getSize(),
                comments.getTotalElements(), comments.getTotalPages(), comments.isLast());
    }

    @Override
    public Comment addComment(CommentRequest commentRequest, Long userId, Long blogId) {
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new ResourceNotFoundException(BLOG_STR, ID_STR, blogId));

        Comment comment = new Comment(commentRequest.getBody());
        comment.setUserId(userId);
        comment.setBlog(blog);

        return commentRepository.save(comment);
    }

    @Override
    public Comment getComment(Long blogId, Long id) {
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new ResourceNotFoundException(BLOG_STR, ID_STR, blogId));
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(COMMENT_STR, ID_STR, id));
        if (comment.getBlog().getId().equals(blog.getId())) {
            return comment;
        }

        throw new BlogapiException(HttpStatus.BAD_REQUEST, COMMENT_DOES_NOT_BELONG_TO_BLOG);
    }

    @Override
    public Comment updateComment(Long blogId, Long id, CommentRequest commentRequest) {
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new ResourceNotFoundException(BLOG_STR, ID_STR, blogId));
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(COMMENT_STR, ID_STR, id));

        if (!comment.getBlog().getId().equals(blog.getId())) {
            throw new BlogapiException(HttpStatus.BAD_REQUEST, COMMENT_DOES_NOT_BELONG_TO_BLOG);
        }

        comment.setBody(commentRequest.getBody());
        return commentRepository.save(comment);

    }

    @Override
    public ApiResponse deleteComment(Long blogId, Long id) {
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new ResourceNotFoundException(BLOG_STR, ID_STR, blogId));
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(COMMENT_STR, ID_STR, id));

        if (!comment.getBlog().getId().equals(blog.getId())) {
            return new ApiResponse(Boolean.FALSE, COMMENT_DOES_NOT_BELONG_TO_BLOG);
        }

        commentRepository.deleteById(comment.getId());
        return new ApiResponse(Boolean.TRUE, "You successfully deleted comment");

    }
}
