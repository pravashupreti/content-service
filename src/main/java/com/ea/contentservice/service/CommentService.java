package com.ea.contentservice.service;

import com.ea.contentservice.model.Comment;
import com.ea.contentservice.payload.ApiResponse;
import com.ea.contentservice.payload.CommentRequest;
import com.ea.contentservice.payload.PagedResponse;

public interface CommentService {

	PagedResponse<Comment> getAllComments(Long blogId, int page, int size);

	Comment addComment(CommentRequest commentRequest, Long userId, Long blogId);

	Comment getComment(Long blogId, Long id);

	Comment updateComment(Long blogId, Long id, CommentRequest commentRequest);

	ApiResponse deleteComment(Long blogId, Long id);

}
