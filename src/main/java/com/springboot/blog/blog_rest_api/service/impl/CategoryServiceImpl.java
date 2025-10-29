package com.springboot.blog.blog_rest_api.service.impl;


import com.springboot.blog.blog_rest_api.dto.CategoryDto;
import com.springboot.blog.blog_rest_api.entity.Category;
import com.springboot.blog.blog_rest_api.exception.ResourceNotFoundException;
import com.springboot.blog.blog_rest_api.repository.CategoryRepository;
import com.springboot.blog.blog_rest_api.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    ModelMapper mapper;
    CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper mapper) {
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {

        Category category = mapToEntity(categoryDto);
        Category savedCategory = categoryRepository.save(category);
        return mapToDto(savedCategory);
    }

    @Override
    public CategoryDto getCategory(long categoryId) {
        Category category =categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","id",categoryId));
        return mapToDto(category);
    }

    @Override
    public List<CategoryDto> getAllCategories() {

        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(category -> mapToDto(category)).collect(Collectors.toList());
    }


    private CategoryDto mapToDto(Category category){
        CategoryDto categoryDto = mapper.map(category,CategoryDto.class);
        return categoryDto;

    }

    private Category mapToEntity(CategoryDto CategoryDto){
        Category category = mapper.map(CategoryDto,Category.class);
        return category;
    }
}
