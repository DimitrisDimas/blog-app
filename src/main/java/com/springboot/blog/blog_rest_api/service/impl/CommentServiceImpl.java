package com.springboot.blog.blog_rest_api.service.impl;

import com.springboot.blog.blog_rest_api.dto.CommentDto;
import com.springboot.blog.blog_rest_api.entity.Comment;
import com.springboot.blog.blog_rest_api.repository.CommentRepository;
import com.springboot.blog.blog_rest_api.service.CommentService;
import org.modelmapper.ModelMapper;

public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private ModelMapper mapper;

    public CommentServiceImpl(CommentRepository commentRepository, ModelMapper mapper) {
        this.commentRepository = commentRepository;
        this.mapper = mapper;
    }



    //Mapper Methods
    private CommentDto mapToDto(Comment comment){
        CommentDto commentDto = mapper.map(comment,CommentDto.class);
        return commentDto;
    }

    private Comment mapToEntity(CommentDto commentDto){
        Comment comment = mapper.map(commentDto,Comment.class);
        return comment;
    }





}
