package com.springboot.blog.blog_rest_api.controller.docs;


import com.springboot.blog.blog_rest_api.dto.PostDto;
import com.springboot.blog.blog_rest_api.dto.PostResponse;
import com.springboot.blog.blog_rest_api.utils.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Post Management", description = "REST APIs for managing posts")
public interface PostControllerDoc {

    @Operation(
            summary = "Create a new post",
            description = "Creates a new post in the database.",
            tags = {"Post Management"},
            responses = {
                    @ApiResponse(description = "Created", responseCode = "201", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            }
    )
    ResponseEntity<PostDto> createPost(@Parameter(description="Post details", required=true) PostDto postDto);

    @Operation(
            summary = "Get all posts",
            description = "Fetches a paginated list of all posts.",
            tags = {"Post Management"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            }
    )
    PostResponse getAllPosts(@Parameter(description="Page number") int pageNo,
                             @Parameter(description="Page size") int pageSize,
                             @Parameter(description="Sort by field") String sortBy,
                             @Parameter(description="Sort direction") String sortDir);

    @Operation(
            summary = "Get post by ID",
            description = "Fetches a single post by its ID.",
            tags = {"Post Management"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            }
    )
    ResponseEntity<PostDto> getPostById(@PathVariable(name="id") long id);

    @Operation(
            summary = "Update a post",
            description = "Updates a specific post by its ID.",
            tags = {"Post Management"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            }
    )
    ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable(name="id") long id);

    @Operation(
            summary = "Delete a post",
            description = "Deletes a specific post by its ID.",
            tags = {"Post Management"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            }
    )
   ResponseEntity<String> deletePost(@PathVariable(name="id") long id);

    @Operation(
            summary = "Get posts by category ID",
            description = "Fetches posts filtered by a category ID.",
            tags = {"Post Management"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            }
    )
    PostResponse getPostsByCategory(@PathVariable("category_id") long category_id,
                                           @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
                                           @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                           @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
                                           @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    );
}
