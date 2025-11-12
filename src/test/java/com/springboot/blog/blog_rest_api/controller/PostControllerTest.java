package com.springboot.blog.blog_rest_api.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.blog.blog_rest_api.dto.PostDto;
import com.springboot.blog.blog_rest_api.security.JwtTokenProvider;
import com.springboot.blog.blog_rest_api.service.PostService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PostController.class)
@AutoConfigureMockMvc(addFilters = false)
public class PostControllerTest {

    @MockitoBean
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PostService postService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    // ----------- Valid Test -----------
    @Test
    public void testCreatePost_ValidRequest_ReturnsCreatedPost() throws Exception {
        // Arrange
        PostDto postDto = new PostDto();
        postDto.setTitle("My First Post");
        postDto.setDescription("This is a detailed description of my first post.");
        postDto.setContent("This is the content of my first post. It has enough length.");
        postDto.setCategoryId(1L);

        PostDto savedPost = new PostDto();
        savedPost.setId(1L);
        savedPost.setTitle(postDto.getTitle());
        savedPost.setDescription(postDto.getDescription());
        savedPost.setContent(postDto.getContent());
        savedPost.setCategoryId(postDto.getCategoryId());

        // Mock service
        Mockito.when(postService.createPost(Mockito.any(PostDto.class))).thenReturn(savedPost);

        // Act & Assert
        mockMvc.perform(post("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("My First Post"))
                .andExpect(jsonPath("$.description").value("This is a detailed description of my first post."))
                .andExpect(jsonPath("$.content").value("This is the content of my first post. It has enough length."))
                .andExpect(jsonPath("$.categoryId").value(1));
    }

    // ----------- Invalid Test -----------
    @Test
    public void testCreatePost_InvalidRequest_ReturnsBadRequest() throws Exception {
        // Arrange
        PostDto postDto = new PostDto();
        postDto.setTitle("A"); // Too short, less than 2 characters
        postDto.setDescription("Short"); // Too short, less than 10 characters
        postDto.setContent(""); // Empty content
        postDto.setCategoryId(null); // Null categoryId

        // Act & Assert
        mockMvc.perform(post("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDto)))
                .andExpect(status().isBadRequest());
    }

}
