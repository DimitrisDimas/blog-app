package com.springboot.blog.blog_rest_api.service.impl;

import com.springboot.blog.blog_rest_api.dto.CommentDto;
import com.springboot.blog.blog_rest_api.entity.Comment;
import com.springboot.blog.blog_rest_api.entity.Post;
import com.springboot.blog.blog_rest_api.exception.ResourceNotFoundException;
import com.springboot.blog.blog_rest_api.repository.CommentRepository;
import com.springboot.blog.blog_rest_api.repository.PostRepository;
import com.springboot.blog.blog_rest_api.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private ModelMapper mapper;
    private PostRepository postRepository;


    public CommentServiceImpl(CommentRepository commentRepository, ModelMapper mapper, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.mapper = mapper;
        this.postRepository = postRepository;
    }

    @Override
    public CommentDto createComment(long postId,CommentDto commentDto) {

        Comment comment = mapToEntity(commentDto);
        //retrieve post entity by id
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        // set post to comment entity
        comment.setPost(post);
        //save comment entity to DB
        Comment newComment = commentRepository.save(comment);

        return mapToDto(newComment);
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
