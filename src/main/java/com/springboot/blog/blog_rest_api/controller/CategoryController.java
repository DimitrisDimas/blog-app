package com.springboot.blog.blog_rest_api.controller;

import com.springboot.blog.blog_rest_api.controller.docs.CategoryControllerDoc;
import com.springboot.blog.blog_rest_api.dto.CategoryDto;
import com.springboot.blog.blog_rest_api.dto.CategoryResponse;
import com.springboot.blog.blog_rest_api.service.CategoryService;
import com.springboot.blog.blog_rest_api.utils.AppConstants;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/categories")
public class CategoryController implements CategoryControllerDoc {

    CategoryService categoryService;


    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @SecurityRequirement( name = "Bear Authentication" )
    @PostMapping
    public ResponseEntity<CategoryDto> addCategory(@Valid @RequestBody CategoryDto categoryDto){
        CategoryDto savedCategory = categoryService.addCategory(categoryDto);
        return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
    }

    @GetMapping("/{category_id}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable("category_id") long category_id){

        CategoryDto categoryDto = categoryService.getCategory(category_id);
        return ResponseEntity.ok(categoryDto);
    }


    @GetMapping
    public ResponseEntity<CategoryResponse> getCategories(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ){
        return ResponseEntity.ok(categoryService.getAllCategories(pageNo, pageSize, sortBy, sortDir));
    }


    @SecurityRequirement(name = "Bear Authentication")
    @PutMapping("/{category_id}")
    public ResponseEntity<CategoryDto> updateCategory(
            @PathVariable("category_id") long category_id,
            @Valid @RequestBody CategoryDto categoryDto) {

        CategoryDto updatedCategory = categoryService.updateCategory(categoryDto, category_id);
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }


    @SecurityRequirement(name = "Bear Authentication")
    @DeleteMapping("/{category_id}")
    public ResponseEntity<String> deleteCategory(@PathVariable("category_id") long category_id){
        categoryService.deleteCategory(category_id);
        return ResponseEntity.ok("Category deleted successfully!!!");
    }

}
