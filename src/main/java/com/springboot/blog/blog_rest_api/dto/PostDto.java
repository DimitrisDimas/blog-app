package com.springboot.blog.blog_rest_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
@Schema(description = "PostDto Model Information")
public class PostDto {

    private long id;
    @Schema(description = "Blog Post Title")
    @NotEmpty
    @Size(min = 2, message = "Post title should have at least 2 characters")
    private String title;

    @Schema(description = "Blog Post Description")
    @NotEmpty
    @Size(min = 10, message = "Post description should have at least 10 characters")
    private String description;

    @Schema(description = "Blog Post Content")
    @NotEmpty
    @Size(min = 10, message = "Post content should have at least 10 characters")
    private String content;
    private Set<CommentDto> comments;

    @NotNull
    @Schema(description = "Blog Post Category")
    private Long categoryId;


}