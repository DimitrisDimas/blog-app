package com.springboot.blog.blog_rest_api.controller;

import com.springboot.blog.blog_rest_api.dto.CommentDto;
import com.springboot.blog.blog_rest_api.service.CommentService;
import jakarta.validation.Valid;
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
    public ResponseEntity<CommentDto> createComment(@PathVariable(value ="post_id")long post_id ,@Valid @RequestBody CommentDto commentDto){
        return new ResponseEntity<>(commentService.createComment(post_id,commentDto), HttpStatus.CREATED);
    }

    // get all comments by one Post
    @GetMapping()
    public List<CommentDto> getCommentsByPostId(@PathVariable(value = "post_id") Long post_id){
        return commentService.getCommentsByPostId(post_id);
    }

    // get comment by post id with comment id
    @GetMapping("/{comment_id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable(value = "post_id") Long post_id,@PathVariable(value = "comment_id") Long comment_id){

        CommentDto commentDto = commentService.getCommentById(comment_id,post_id);
        return new ResponseEntity<>(commentDto,HttpStatus.OK);

    }
    //update comment by post id with comment id
    @PutMapping("/{comment_id}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable(value = "post_id") Long post_id,@PathVariable(value = "comment_id") Long comment_id,@Valid @RequestBody CommentDto updateCommentDto){

        CommentDto commentDto = commentService.updateComment(post_id,comment_id,updateCommentDto);

        return new ResponseEntity<>(commentDto,HttpStatus.OK);

    }

    //delete comment by post id with comment id
    @DeleteMapping("/{comment_id}")
    public ResponseEntity<String> deletePost(@PathVariable(value = "post_id") Long post_id,@PathVariable(value = "comment_id") Long comment_id){
        commentService.deleteCommentById(post_id,comment_id);
        return new ResponseEntity<>("Comment entity deleted successfully",HttpStatus.OK);
    }




}
