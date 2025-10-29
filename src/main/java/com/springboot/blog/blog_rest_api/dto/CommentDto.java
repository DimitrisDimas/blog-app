package com.springboot.blog.blog_rest_api.dto;

import lombok.Data;

@Data
public class CommentDto {

    private long id;
    private String body;
    private String email;
    private String name;
}
