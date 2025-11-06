package com.springboot.blog.blog_rest_api.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {


    private long id;

    @NotEmpty(message = "Name should not be null or empty")
    @Schema(description = "Blog Category Name")
    private String name;

    @NotEmpty
    @Size(min = 10, message = "Category description should have at least 10 characters")
    @Schema(description = "Blog Category Description")
    private String description;
}