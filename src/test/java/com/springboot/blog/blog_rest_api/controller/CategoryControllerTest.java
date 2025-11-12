package com.springboot.blog.blog_rest_api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.blog.blog_rest_api.dto.CategoryDto;
import com.springboot.blog.blog_rest_api.exception.ResourceNotFoundException;
import com.springboot.blog.blog_rest_api.security.JwtTokenProvider;
import com.springboot.blog.blog_rest_api.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoryController.class)
@AutoConfigureMockMvc(addFilters = false) // Disable Spring Security filters
public class CategoryControllerTest {

    @MockitoBean
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CategoryService categoryService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    //Valid Test

    @Test
    public void testAddValidCategory() throws Exception {

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName("Tech");
        categoryDto.setDescription("Tech category");

        CategoryDto savedCategory = new CategoryDto();
        savedCategory.setId(1L);
        savedCategory.setName("Tech");
        savedCategory.setDescription("Tech category");

        Mockito.when(categoryService.addCategory(any(CategoryDto.class)))
                .thenReturn(savedCategory);

        // Act & Assert
        mockMvc.perform(post("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Tech"))
                .andExpect(jsonPath("$.description").value("Tech category"));
    }

    @Test
    public void testGetCategoryByValidId_ReturnsCategory() throws Exception {
        // Arrange
        long categoryId = 1L;
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(categoryId);
        categoryDto.setName("Tech");
        categoryDto.setDescription("Tech category");

        // Mock the service
        Mockito.when(categoryService.getCategory(categoryId)).thenReturn(categoryDto);

        // Act & Assert
        mockMvc.perform(get("/api/categories/{category_id}", categoryId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(categoryId))
                .andExpect(jsonPath("$.name").value("Tech"))
                .andExpect(jsonPath("$.description").value("Tech category"));
    }


    //Invalid Test

    @Test
    public void testAddInvalidCategory_MissingName() throws Exception {
        // Arrange
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setDescription("Tech category"); // Missing name

        // Act & Assert
        mockMvc.perform(post("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryDto)))
                .andExpect(status().isBadRequest());
    }


    @Test
    public void testGetCategoryByInvalidId_ReturnsCategory() throws Exception {
        // Arrange
        long categoryId = 999L;

        // Mock service to throw an exception (assuming you throw ResourceNotFoundException)
        Mockito.when(categoryService.getCategory(categoryId))
                .thenThrow(new ResourceNotFoundException("Category","id",categoryId));

        // Act & Assert
        mockMvc.perform(get("/api/categories/{category_id}", categoryId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }




}
