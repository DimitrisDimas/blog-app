package com.springboot.blog.blog_rest_api.service.impl;

import com.springboot.blog.blog_rest_api.dto.PostDto;
import com.springboot.blog.blog_rest_api.entity.Post;
import com.springboot.blog.blog_rest_api.repository.PostRepository;
import com.springboot.blog.blog_rest_api.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


@Service
public class PostServiceImpl implements PostService {
    private ModelMapper mapper;
    private PostRepository postRepository;

    public PostServiceImpl(ModelMapper mapper, PostRepository postRepository) {
        this.mapper = mapper;
        this.postRepository = postRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {

        Post post = mapToEntity(postDto);
        Post newPost = postRepository.save(post);
        return mapToDTO(newPost);
    }


    // --- Mapping helpers ---
    private PostDto mapToDTO(Post post) {

        PostDto postDto = mapper.map(post, PostDto.class);
        return postDto;
    }

    private Post mapToEntity(PostDto postDto) {
        Post post = mapper.map(postDto, Post.class);
        return post;
    }

}
