package com.springboot.blog.blog_rest_api.dto;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {


    private long id;
    private String name;
    private String description;
}