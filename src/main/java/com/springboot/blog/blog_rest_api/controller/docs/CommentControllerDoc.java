package com.springboot.blog.blog_rest_api.controller.docs;

import com.springboot.blog.blog_rest_api.dto.CommentDto;
import com.springboot.blog.blog_rest_api.dto.CommentResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Comment Management", description = "REST APIs for managing comments")
public interface CommentControllerDoc {

    @Operation(
            summary = "Create a new comment",
            description = "Creates a new comment for a given post.",
            tags = {"Comment Management"},
            responses = {
                    @ApiResponse(description = "Created", responseCode = "201", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            }
    )
    ResponseEntity<CommentDto> createComment(@PathVariable(value ="post_id")long post_id , @Valid @RequestBody CommentDto commentDto);

    @Operation(
            summary = "Get all comments by post",
            description = "Fetches a paginated list of comments for a specific post.",
            tags = {"Comment Management"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            }
    )
    CommentResponse getCommentsByPostId(
            @Parameter(name="post_id", required=true) Long post_id,
            @Parameter(name="pageNo") int pageNo,
            @Parameter(name="pageSize") int pageSize,
            @Parameter(name="sortBy") String sortBy,
            @Parameter(name="sortDir") String sortDir);

    @Operation(
            summary = "Get comment by post ID and comment ID",
            description = "Fetches a single comment by post ID and comment ID.",
            tags = {"Comment Management"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            }
    )
    ResponseEntity<CommentDto> getCommentById(@Parameter(name="post_id", required=true) Long post_id, @Parameter(name="comment_id", required=true) Long comment_id);

    @Operation(
            summary = "Update a comment",
            description = "Updates a specific comment by post ID and comment ID.",
            tags = {"Comment Management"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            }
    )
    ResponseEntity<CommentDto> updateComment(@Parameter(name="post_id", required=true) Long post_id, @Parameter(name="comment_id", required=true) Long comment_id, @Parameter(name="updateCommentDto", required=true) CommentDto updateCommentDto);

    @Operation(
            summary = "Delete a comment",
            description = "Deletes a specific comment by post ID and comment ID.",
            tags = {"Comment Management"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            }
    )
   ResponseEntity<String> deletePost(@PathVariable(value = "post_id") Long post_id,@PathVariable(value = "comment_id") Long comment_id);
}