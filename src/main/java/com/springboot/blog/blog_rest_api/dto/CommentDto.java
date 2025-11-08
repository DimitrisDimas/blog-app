package com.springboot.blog.blog_rest_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentDto {

    private long id;

    @NotEmpty
    @Size(min = 10, message = "Body should have at minimum 10 characters")
    @Schema(description = "Blog Comment Body")
    private String body;

    @NotEmpty(message = "Email should not be null or empty")
    @Email
    @Schema(description = "Blog Comment Email")
    private String email;

    @NotEmpty(message = "Name should not be null or empty")
    @Schema(description = "Blog Comment Name")
    private String name;
}
