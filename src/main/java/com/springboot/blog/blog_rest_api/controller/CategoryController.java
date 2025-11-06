package com.springboot.blog.blog_rest_api.controller;

import com.springboot.blog.blog_rest_api.dto.CategoryDto;
import com.springboot.blog.blog_rest_api.dto.CategoryResponse;
import com.springboot.blog.blog_rest_api.service.CategoryService;
import com.springboot.blog.blog_rest_api.utils.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@Tag( name = "REST APIs for Category Resource" )
public class CategoryController {

    CategoryService categoryService;


    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    @Operation(summary = "Create Category REST API", description = "Create Category REST API is used to save category into database")
    @ApiResponse(responseCode = "201", description = "Http Status 201 CREATED")
    @SecurityRequirement( name = "Bear Authentication" )
    @PostMapping
    public ResponseEntity<CategoryDto> addCategory(@Valid @RequestBody CategoryDto categoryDto){
        CategoryDto savedCategory = categoryService.addCategory(categoryDto);
        return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
    }
    @Operation(summary = "Get Category By Id REST API", description = "Get Category By Id REST API is used to get single category from the database")
    @ApiResponse(responseCode = "200", description = "Http Status 200 SUCCESS")
    @GetMapping("/{category_id}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable("category_id") long category_id){

        CategoryDto categoryDto = categoryService.getCategory(category_id);
        return ResponseEntity.ok(categoryDto);
    }

    @Operation(summary = "Get All Categories REST API", description = "Get All Categories REST API is used to fetch all the categories from the database")
    @ApiResponse(responseCode = "200", description = "Http Status 200 SUCCESS")
    @GetMapping
    public ResponseEntity<CategoryResponse> getCategories(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ){
        return ResponseEntity.ok(categoryService.getAllCategories(pageNo, pageSize, sortBy, sortDir));
    }

    @Operation(summary = "update Category REST API", description = "Update Category REST API is used to update a particular category in the database")
    @ApiResponse(responseCode = "200", description = "Http Status 200 SUCCESS")
    @SecurityRequirement(name = "Bear Authentication")
    @PutMapping("/{category_id}")
    public ResponseEntity<CategoryDto> updateCategory(
            @PathVariable("category_id") long category_id,
            @Valid @RequestBody CategoryDto categoryDto) {

        CategoryDto updatedCategory = categoryService.updateCategory(categoryDto, category_id);
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }

    @Operation(summary = "Delete Category REST API", description = "Delete Category REST API is used to delete a particular category from the database")
    @ApiResponse(responseCode = "200", description = "Http Status 200 SUCCESS")
    @SecurityRequirement(name = "Bear Authentication")
    @DeleteMapping("/{category_id}")
    public ResponseEntity<String> deleteCategory(@PathVariable("category_id") long category_id){
        categoryService.deleteCategory(category_id);
        return ResponseEntity.ok("Category deleted successfully!!!");
    }

}
