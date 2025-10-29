package com.springboot.blog.blog_rest_api.controller;

import com.springboot.blog.blog_rest_api.dto.CategoryDto;
import com.springboot.blog.blog_rest_api.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    CategoryService categoryService;


    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    //Build add Category Rest API
    @PostMapping
    public ResponseEntity<CategoryDto> addCategory(@RequestBody CategoryDto categoryDto){
        CategoryDto savedCategory = categoryService.addCategory(categoryDto);
        return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
    }

    //Build Get Category REST API
    @GetMapping("/{category_id}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable("category_id") long category_id){

        CategoryDto categoryDto = categoryService.getCategory(category_id);
        return ResponseEntity.ok(categoryDto);
    }

    //Build get all Categories Rest Api
    @GetMapping
    public ResponseEntity<List<CategoryDto>> getCategories(){
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    //Update category by id
    @PutMapping("/{category_id}")
    public ResponseEntity<CategoryDto> updateCategory(
            @PathVariable("category_id") long category_id,
            @RequestBody CategoryDto categoryDto) {

        CategoryDto updatedCategory = categoryService.updateCategory(categoryDto, category_id);
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }

    //Delete Category by id
    @DeleteMapping("/{category_id}")
    public ResponseEntity<String> deleteCategory(@PathVariable("category_id") long category_id){
        categoryService.deleteCategory(category_id);
        return ResponseEntity.ok("Category deleted successfully!!!");
    }

}
