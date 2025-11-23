package com.springboot.blog.blog_rest_api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.blog.blog_rest_api.dto.CommentDto;
import com.springboot.blog.blog_rest_api.dto.LoginDto;
import com.springboot.blog.blog_rest_api.entity.*;
import com.springboot.blog.blog_rest_api.repository.*;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    Post post;

    @BeforeEach
    public void setup() {
        if (!roleRepository.findByName("ROLE_USER").isPresent()) {
            Role roleUser = new Role();
            roleUser.setName("ROLE_USER");
            roleRepository.save(roleUser);
        }
        addUserToDB();
        addPostWithComment();
    }


    // 1 Create Comment (201)
    @Test
    public void testCreateComment_Success() throws Exception {


        String token = loginAndGetToken("Masatos", "12345");

        CommentDto commentDto = new CommentDto();
        commentDto.setName("Jane Doe");
        commentDto.setEmail("jane@example.com");
        commentDto.setBody("This is a valid comment body with more than 10 characters");

        mockMvc.perform(post("/api/posts/" + post.getId() + "/comments")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(commentDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Jane Doe"))
                .andExpect(jsonPath("$.email").value("jane@example.com"))
                .andExpect(jsonPath("$.body").value("This is a valid comment body with more than 10 characters"));
    }

    private String loginAndGetToken(String username, String password) throws Exception {
        LoginDto loginDto = new LoginDto();
        loginDto.setUsernameOrEmail("Masatos");
        loginDto.setPassword("12345");

        String loginResponse = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDto)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String token = objectMapper.readTree(loginResponse).get("accessToken").asText();
        return token;
    }

    private void addUserToDB(){
        User user = new User();
        user.setName("Masatos");
        user.setUsername("Masatos");
        user.setEmail("Masatos@example.com");
        user.setPassword(passwordEncoder.encode("12345"));
        Role roleUser = roleRepository.findByName("ROLE_USER").get();
        user.setRoles(Set.of(roleUser));
        userRepository.save(user);
    }

    private void addPostWithComment() {
        Category category = categoryRepository.findAll().stream().findFirst().orElseGet(() -> {
            Category newCategory = new Category();
            newCategory.setName("Technology");
            newCategory.setDescription("All about tech");
            return categoryRepository.save(newCategory);
        });

        post = new Post();
        post.setTitle("My First Post");
        post.setDescription("This is a description of my first post");
        post.setContent("This is the full content of my first post, more than 10 characters");
        post.setCategory(category);
        post = postRepository.save(post);

        Comment comment = new Comment();
        comment.setName("Jane Doe");
        comment.setEmail("jane@example.com");
        comment.setBody("This is a comment body with more than 10 characters");
        comment.setPost(post);
        commentRepository.save(comment);

    }

}
