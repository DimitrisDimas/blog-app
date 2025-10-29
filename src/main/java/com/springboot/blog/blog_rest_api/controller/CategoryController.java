package com.springboot.blog.blog_rest_api.controller;

import com.springboot.blog.blog_rest_api.service.CategoryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class CategoryController {

    CategoryService categoryService;


    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
}
