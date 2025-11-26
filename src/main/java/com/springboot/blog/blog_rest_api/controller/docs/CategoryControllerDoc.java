package com.springboot.blog.blog_rest_api.controller.docs;

import com.springboot.blog.blog_rest_api.dto.CategoryDto;
import com.springboot.blog.blog_rest_api.dto.CategoryResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Category Management", description = "REST APIs for managing blog categories")
public interface CategoryControllerDoc {

    @Operation(
            summary = "Create a new Category",
            description = "Registers a new category in the system.",
            tags = {"Category Management"},
            responses = {
                    @ApiResponse(description = "Created", responseCode = "201", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Conflict", responseCode = "409", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            }
    )
    ResponseEntity<CategoryDto> addCategory(CategoryDto categoryDto);

    @Operation(
            summary = "Retrieve category by ID",
            description = "Fetches a single category based on its ID.",
            tags = {"Category Management"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            }
    )
    ResponseEntity<CategoryDto> getCategory(long category_id);

    @Operation(
            summary = "Retrieve all categories",
            description = "Fetches a list of all categories registered in the system.",
            tags = {"Category Management"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            }
    )
    ResponseEntity<CategoryResponse> getCategories(int pageNo,
                                                   int pageSize,
                                                   String sortBy,
                                                   String sortDir);

    @Operation(
            summary = "Update an existing category",
            description = "Updates a category's information using the provided ID.",
            tags = {"Category Management"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            }
    )
    ResponseEntity<CategoryDto> updateCategory(long category_id, CategoryDto categoryDto);

    @Operation(
            summary = "Delete a category",
            description = "Removes a category from the system using its ID.",
            tags = {"Category Management"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            }
    )
    ResponseEntity<String> deleteCategory(long category_id);
}
