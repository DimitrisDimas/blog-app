package com.springboot.blog.blog_rest_api.service;

import com.springboot.blog.blog_rest_api.dto.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(long postId, CommentDto commentDto);

    List<CommentDto> getCommentsByPostId(Long post_id);
}
