package com.springboot.blog.blog_rest_api.controller;

import com.springboot.blog.blog_rest_api.dto.CommentDto;
import com.springboot.blog.blog_rest_api.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/posts/{post_id}/comments")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }


    // create comment post REST API
    @PostMapping
    public ResponseEntity<CommentDto> createComment(@PathVariable(value ="post_id")long post_id ,@RequestBody CommentDto commentDto){
        return new ResponseEntity<>(commentService.createComment(post_id,commentDto), HttpStatus.CREATED);
    }

    // get all comments by one Post
    @GetMapping()
    public List<CommentDto> getCommentsByPostId(@PathVariable(value = "post_id") Long post_id){
        return commentService.getCommentsByPostId(post_id);
    }



}
