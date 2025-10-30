package com.springboot.blog.blog_rest_api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentDto {

    private long id;
    @NotEmpty
    @Size(min = 10, message = "Body should have at minimum 10 characters")
    private String body;
    @NotEmpty(message = "Email should not be null or empty")
    @Email
    private String email;
    @NotEmpty(message = "Name should not be null or empty")
    private String name;
}
