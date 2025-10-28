package com.springboot.blog.blog_rest_api.dto;

import lombok.Data;

@Data


public class PostDto {

    private long id;
    private String title;
    private String description;
    private String content;



}