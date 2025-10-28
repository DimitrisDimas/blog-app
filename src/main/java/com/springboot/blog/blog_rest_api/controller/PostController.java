package com.springboot.blog.blog_rest_api.controller;

import com.springboot.blog.blog_rest_api.service.PostService;

public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }
}
