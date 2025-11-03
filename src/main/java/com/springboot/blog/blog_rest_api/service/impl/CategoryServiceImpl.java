package com.springboot.blog.blog_rest_api.service.impl;


import com.springboot.blog.blog_rest_api.dto.CategoryDto;
import com.springboot.blog.blog_rest_api.dto.CategoryResponse;
import com.springboot.blog.blog_rest_api.dto.CommentDto;
import com.springboot.blog.blog_rest_api.dto.CommentResponse;
import com.springboot.blog.blog_rest_api.entity.Category;
import com.springboot.blog.blog_rest_api.entity.Comment;
import com.springboot.blog.blog_rest_api.exception.ResourceNotFoundException;
import com.springboot.blog.blog_rest_api.repository.CategoryRepository;
import com.springboot.blog.blog_rest_api.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public CategoryResponse getAllCategories(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        // create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Category> categories = categoryRepository.findAll(pageable);

        // get content for page object
        List<Category> listsOfCategories = categories.getContent();

        List<CategoryDto> content= listsOfCategories.stream().map(category -> mapToDto(category)).collect(Collectors.toList());

        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(content);
        categoryResponse.setPageNo(categories.getNumber());
        categoryResponse.setPageSize(categories.getSize());
        categoryResponse.setTotalElements(categories.getTotalElements());
        categoryResponse.setTotalPages(categories.getTotalPages());
        categoryResponse.setLast(categories.isLast());

        return categoryResponse;
        
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, long id) {

        Category category = categoryRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Category","id",id));

        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        category.setId(id);

        Category categoryUpdated = categoryRepository.save(category);


        return mapToDto(categoryUpdated);
    }

    @Override
    public void deleteCategory(long category_id) {
        Category category = categoryRepository.findById(category_id).orElseThrow(()->new ResourceNotFoundException("Category","id",category_id));
        categoryRepository.delete(category);
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
