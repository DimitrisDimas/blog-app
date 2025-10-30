package com.springboot.blog.blog_rest_api.dto;
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
    private String name;
    @NotEmpty
    @Size(min = 10, message = "Category description should have at least 10 characters")
    private String description;
}