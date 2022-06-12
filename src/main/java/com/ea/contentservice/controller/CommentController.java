package com.ea.contentservice.controller;

import com.ea.contentservice.model.Comment;
import com.ea.contentservice.payload.ApiResponse;
import com.ea.contentservice.payload.CommentRequest;
import com.ea.contentservice.payload.PagedResponse;

import com.ea.contentservice.service.CommentService;
import com.ea.contentservice.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/blogs/{blogId}/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping
    public ResponseEntity<PagedResponse<Comment>> getAllComments(@PathVariable(name = "blogId") Long blogId,
                                                                 @RequestParam(name = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
                                                                 @RequestParam(name = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size) {

        PagedResponse<Comment> allComments = commentService.getAllComments(blogId, page, size);

        return new ResponseEntity<>(allComments, HttpStatus.OK);
    }

    @PostMapping("/{userId}")
    public ResponseEntity<Comment> addComment(@Valid @RequestBody CommentRequest commentRequest, @PathVariable(name = "userId") Long userId,
                                              @PathVariable(name = "blogId") Long blogId) {
        Comment newComment = commentService.addComment(commentRequest, userId, blogId);

        return new ResponseEntity<>(newComment, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comment> getComment(@PathVariable(name = "blogId") Long blogId,
                                              @PathVariable(name = "id") Long id) {
        Comment comment = commentService.getComment(blogId, id);

        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable(name = "blogId") Long blogId,
                                                 @PathVariable(name = "id") Long id, @Valid @RequestBody CommentRequest commentRequest
    ) {

        Comment updatedComment = commentService.updateComment(blogId, id, commentRequest);

        return new ResponseEntity<>(updatedComment, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable(name = "blogId") Long blogId,
                                                     @PathVariable(name = "id") Long id) {

        ApiResponse response = commentService.deleteComment(blogId, id);

        HttpStatus status = response.getSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;

        return new ResponseEntity<>(response, status);
    }

}
