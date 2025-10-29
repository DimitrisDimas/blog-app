package com.springboot.blog.blog_rest_api.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {


    private long id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String description;
}