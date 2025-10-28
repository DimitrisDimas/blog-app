package com.springboot.blog.blog_rest_api.service;

import com.springboot.blog.blog_rest_api.dto.PostDto;

public interface PostService {
    PostDto createPost(PostDto postDto);
}
