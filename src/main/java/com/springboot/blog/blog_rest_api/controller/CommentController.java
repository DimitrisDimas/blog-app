package com.springboot.blog.blog_rest_api.controller;

import com.springboot.blog.blog_rest_api.dto.CommentDto;
import com.springboot.blog.blog_rest_api.dto.CommentResponse;
import com.springboot.blog.blog_rest_api.service.CommentService;
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
@RequestMapping("api/posts/{post_id}/comments")
@Tag( name = "REST APIs for Comment Resource" )
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }


    @Operation(summary = "Create Comment REST API", description = "Create Comment REST API is used to save comment into database")
    @ApiResponse(responseCode = "201", description = "Http Status 201 CREATED")
    @SecurityRequirement( name = "Bear Authentication" )
    @PostMapping
    public ResponseEntity<CommentDto> createComment(@PathVariable(value ="post_id")long post_id ,@Valid @RequestBody CommentDto commentDto){
        return new ResponseEntity<>(commentService.createComment(post_id,commentDto), HttpStatus.CREATED);
    }

    @Operation(summary = "Get All Comments By One Post REST API", description = "Get All Comments by one post REST API is used to fetch all the comments by one post from the database")
    @ApiResponse(responseCode = "200", description = "Http Status 200 SUCCESS")
    @GetMapping()
    public CommentResponse getCommentsByPostId(@PathVariable(value = "post_id") Long post_id,
                                               @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
                                               @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                               @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
                                               @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ){
        return commentService.getCommentsByPostId(post_id,pageNo, pageSize, sortBy, sortDir);
    }

    @Operation(summary = "Get Comment By post ID and comment ID REST API", description = "Get Comment By PostID and CommentID REST API is used to get single comment by PostID and CommentID from the database")
    @ApiResponse(responseCode = "200", description = "Http Status 200 SUCCESS")
    @GetMapping("/{comment_id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable(value = "post_id") Long post_id,@PathVariable(value = "comment_id") Long comment_id){

        CommentDto commentDto = commentService.getCommentById(comment_id,post_id);
        return new ResponseEntity<>(commentDto,HttpStatus.OK);

    }
    @Operation(summary = "update Comment REST API", description = "Update Comment REST API is used to update a particular comment in the database")
    @ApiResponse(responseCode = "200", description = "Http Status 200 SUCCESS")
    @SecurityRequirement(name = "Bear Authentication")
    @PutMapping("/{comment_id}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable(value = "post_id") Long post_id,@PathVariable(value = "comment_id") Long comment_id,@Valid @RequestBody CommentDto updateCommentDto){

        CommentDto commentDto = commentService.updateComment(post_id,comment_id,updateCommentDto);

        return new ResponseEntity<>(commentDto,HttpStatus.OK);

    }

    @Operation(summary = "Delete Comment By PostID and CommentID REST API", description = "Delete Comment REST API is used to delete a particular comment from the database")
    @ApiResponse(responseCode = "200", description = "Http Status 200 SUCCESS")
    @SecurityRequirement(name = "Bear Authentication")
    @DeleteMapping("/{comment_id}")
    public ResponseEntity<String> deletePost(@PathVariable(value = "post_id") Long post_id,@PathVariable(value = "comment_id") Long comment_id){
        commentService.deleteCommentById(post_id,comment_id);
        return new ResponseEntity<>("Comment entity deleted successfully",HttpStatus.OK);
    }




}
