package com.springboot.blog.blog_rest_api.service;

import com.springboot.blog.blog_rest_api.dto.CommentDto;

public interface CommentService {
    CommentDto createComment(long postId, CommentDto commentDto);
}
