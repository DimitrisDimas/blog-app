package com.springboot.blog.blog_rest_api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.blog.blog_rest_api.dto.LoginDto;
import com.springboot.blog.blog_rest_api.dto.RegisterDto;
import com.springboot.blog.blog_rest_api.entity.Role;
import com.springboot.blog.blog_rest_api.entity.User;
import com.springboot.blog.blog_rest_api.repository.RoleRepository;
import com.springboot.blog.blog_rest_api.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class AuthControllerTest {

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

    @BeforeEach
    public void setup() {
        if (!roleRepository.findByName("ROLE_USER").isPresent()) {
            Role roleUser = new Role();
            roleUser.setName("ROLE_USER");
            roleRepository.save(roleUser);
        }
    }

    // Created (201)
    @Test
    public void testRegisterUser_Success() throws Exception {
        RegisterDto dto = createValidRegisterDto("john123", "john@example.com");

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(content().string("User registered successfully"));

        User user = userRepository.findByUsername("john123").orElse(null);
        assertNotNull(user);
        assertEquals("john123", user.getUsername());
        assertTrue(passwordEncoder.matches("12345", user.getPassword()));
        assertTrue(user.getRoles().stream().anyMatch(r -> r.getName().equals("ROLE_USER")));
    }

    // Bad Request (400)
    @Test
    public void testRegisterUser_InvalidUsername() throws Exception {
        RegisterDto dto = createValidRegisterDto("abc", "invalid@example.com"); // username < 5 chars

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    // Bad Request (400)
    @Test
    public void testRegisterUser_InvalidEmail() throws Exception {
        RegisterDto dto = createValidRegisterDto("validUser", "invalid-email"); // invalid email

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    // Conflict (409)
    @Test
    public void testRegisterUser_DuplicateUsername() throws Exception {
        // Create first user
        RegisterDto dto = createValidRegisterDto("john123", "john@example.com");
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());

        // Create second user with the same username
        RegisterDto dtoDuplicate = createValidRegisterDto("john123", "john2@example.com");

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoDuplicate)))
                .andExpect(status().isConflict());
    }

    //-----------------------------------------------------------------------

    //  Success (200)
    @Test
    public void testLogin_Success() throws Exception {

        AddUserToDB();

        LoginDto loginDto = new LoginDto();
        loginDto.setUsernameOrEmail("john123");
        loginDto.setPassword("12345");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").isNotEmpty());
    }

    //  Bad Request (400)
    @Test
    public void testLogin_MissingPassword() throws Exception {
        AddUserToDB();
        LoginDto loginDto = new LoginDto();
        loginDto.setUsernameOrEmail("john123");
        loginDto.setPassword("");
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDto)))
                .andExpect(status().isBadRequest());
    }

    // Unauthorized (401)
    @Test
    public void testLogin_InvalidCredentials() throws Exception {

        AddUserToDB();
        LoginDto loginDto = new LoginDto();
        loginDto.setUsernameOrEmail("john123");
        loginDto.setPassword("wrongpassword");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDto)))
                .andExpect(status().isUnauthorized());
    }

    // ---------------- Helper Methods ----------------
    private RegisterDto createValidRegisterDto(String username, String email) {
        RegisterDto dto = new RegisterDto();
        dto.setName("John Doe");
        dto.setUsername(username);
        dto.setEmail(email);
        dto.setPassword("12345");
        return dto;
    }

    private void AddUserToDB(){
        User user = new User();
        user.setName("John Doe");
        user.setUsername("john123");
        user.setEmail("john@example.com");
        user.setPassword(passwordEncoder.encode("12345"));
        Role roleUser = roleRepository.findByName("ROLE_USER").get();
        user.setRoles(Set.of(roleUser));
        userRepository.save(user);
    }
}
