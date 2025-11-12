package com.springboot.blog.blog_rest_api.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.blog.blog_rest_api.dto.CategoryDto;
import com.springboot.blog.blog_rest_api.dto.PostDto;
import com.springboot.blog.blog_rest_api.dto.PostResponse;
import com.springboot.blog.blog_rest_api.exception.ResourceNotFoundException;
import com.springboot.blog.blog_rest_api.security.JwtTokenProvider;
import com.springboot.blog.blog_rest_api.service.CategoryService;
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
import java.util.Arrays;
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

    @MockitoBean
    private CategoryService categoryService;

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

    @Test
    public void testGetAllPosts_ReturnsPaginatedPosts() throws Exception {
        // Arrange
        int pageNo = 0;
        int pageSize = 2;
        String sortBy = "title";
        String sortDir = "asc";

        // Mock post data
        PostDto post1 = new PostDto();
        post1.setId(1L);
        post1.setTitle("First Post");
        post1.setDescription("Description of first post");
        post1.setContent("Content of first post");
        post1.setCategoryId(1L);

        PostDto post2 = new PostDto();
        post2.setId(2L);
        post2.setTitle("Second Post");
        post2.setDescription("Description of second post");
        post2.setContent("Content of second post");
        post2.setCategoryId(2L);

        List<PostDto> posts = Arrays.asList(post1, post2);

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(posts);
        postResponse.setPageNo(pageNo);
        postResponse.setPageSize(pageSize);
        postResponse.setTotalElements(2L);
        postResponse.setTotalPages(1);
        postResponse.setLast(true);

        // Mock service
        Mockito.when(postService.getAllPosts(pageNo, pageSize, sortBy, sortDir)).thenReturn(postResponse);

        // Act & Assert
        mockMvc.perform(get("/api/posts")
                        .param("pageNo", String.valueOf(pageNo))
                        .param("pageSize", String.valueOf(pageSize))
                        .param("sortBy", sortBy)
                        .param("sortDir", sortDir)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].title").value("First Post"))
                .andExpect(jsonPath("$.content[1].id").value(2))
                .andExpect(jsonPath("$.content[1].title").value("Second Post"))
                .andExpect(jsonPath("$.pageNo").value(pageNo))
                .andExpect(jsonPath("$.pageSize").value(pageSize))
                .andExpect(jsonPath("$.totalElements").value(2))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.last").value(true));
    }

    @Test
    public void testGetPostById_ValidId_ReturnsPost() throws Exception {
        // Arrange
        long postId = 1L;

        PostDto postDto = new PostDto();
        postDto.setId(postId);
        postDto.setTitle("My First Post");
        postDto.setDescription("This is the description of my first post.");
        postDto.setContent("This is the content of my first post.");
        postDto.setCategoryId(1L);

        // Mock service
        Mockito.when(postService.getPostById(postId)).thenReturn(postDto);

        // Act & Assert
        mockMvc.perform(get("/api/posts/{id}", postId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(postId))
                .andExpect(jsonPath("$.title").value("My First Post"))
                .andExpect(jsonPath("$.description").value("This is the description of my first post."))
                .andExpect(jsonPath("$.content").value("This is the content of my first post."))
                .andExpect(jsonPath("$.categoryId").value(1));
    }

    @Test
    public void testDeletePost_ValidId_ReturnsSuccessMessage() throws Exception {
        // Arrange
        long postId = 1L;

        // Mock service (void method)
        Mockito.doNothing().when(postService).deletePostById(postId);

        // Act & Assert
        mockMvc.perform(delete("/api/posts/{id}", postId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Post entity deleted successfully"));
    }

    @Test
    public void testUpdatePost_ValidRequest_ReturnsUpdatedPost() throws Exception {
        // Arrange
        long postId = 1L;

        PostDto postDto = new PostDto();
        postDto.setTitle("Updated Title");
        postDto.setDescription("Updated description for the post.");
        postDto.setContent("Updated content of the post.");
        postDto.setCategoryId(1L);

        PostDto updatedPost = new PostDto();
        updatedPost.setId(postId);
        updatedPost.setTitle(postDto.getTitle());
        updatedPost.setDescription(postDto.getDescription());
        updatedPost.setContent(postDto.getContent());
        updatedPost.setCategoryId(postDto.getCategoryId());

        // Mock service
        Mockito.when(postService.updatePost(Mockito.any(PostDto.class), Mockito.eq(postId)))
                .thenReturn(updatedPost);

        // Act & Assert
        mockMvc.perform(put("/api/posts/{id}", postId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(postId))
                .andExpect(jsonPath("$.title").value("Updated Title"))
                .andExpect(jsonPath("$.description").value("Updated description for the post."))
                .andExpect(jsonPath("$.content").value("Updated content of the post."))
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

    @Test
    public void testGetPostById_InvalidId_ReturnsNotFound() throws Exception {
        // Arrange
        long invalidPostId = 999L;

        // Mock service to throw exception
        Mockito.when(postService.getPostById(invalidPostId))
                .thenThrow(new ResourceNotFoundException("Post","id",invalidPostId));

        // Act & Assert
        mockMvc.perform(get("/api/posts/{id}", invalidPostId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

    @Test
    public void testDeletePost_InvalidId_ReturnsNotFound() throws Exception {
        // Arrange
        long invalidPostId = 999L;

        // Mock service to throw exception
        Mockito.doThrow(new ResourceNotFoundException("Post","id",invalidPostId))
                .when(postService).deletePostById(invalidPostId);

        // Act & Assert
        mockMvc.perform(delete("/api/posts/{id}", invalidPostId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdatePost_InvalidId_ReturnsNotFound() throws Exception {
        // Arrange
        long invalidPostId = 999L;

        PostDto postDto = new PostDto();
        postDto.setTitle("Updated Title");
        postDto.setDescription("Updated description");
        postDto.setContent("Updated content");
        postDto.setCategoryId(1L);

        // Mock service to throw exception
        Mockito.when(postService.updatePost(Mockito.any(PostDto.class), Mockito.eq(invalidPostId)))
                .thenThrow(new ResourceNotFoundException("Post","id",invalidPostId));

        // Act & Assert
        mockMvc.perform(put("/api/posts/{id}", invalidPostId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDto)))
                .andExpect(status().isNotFound());
    }
}
