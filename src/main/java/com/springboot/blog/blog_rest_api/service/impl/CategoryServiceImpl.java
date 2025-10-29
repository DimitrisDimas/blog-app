package com.springboot.blog.blog_rest_api.service.impl;


import com.springboot.blog.blog_rest_api.repository.CategoryRepository;
import com.springboot.blog.blog_rest_api.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    ModelMapper mapper;
    CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper mapper) {
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }
}
