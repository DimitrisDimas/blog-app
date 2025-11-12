package com.springboot.blog.blog_rest_api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.blog.blog_rest_api.dto.CategoryDto;
import com.springboot.blog.blog_rest_api.dto.CategoryResponse;
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

import java.util.ArrayList;
import java.util.List;

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

    @Test
    public void testGetCategories_ReturnsPaginatedCategories() throws Exception {
        // Arrange
        int pageNo = 0;
        int pageSize = 10;
        String sortBy = "name";
        String sortDir = "asc";

        // Mock data
        List<CategoryDto> categories = new ArrayList<>();
        CategoryDto cat1 = new CategoryDto();
        cat1.setId(1L);
        cat1.setName("Tech");
        cat1.setDescription("Tech category");
        categories.add(cat1);

        CategoryDto cat2 = new CategoryDto();
        cat2.setId(2L);
        cat2.setName("Books");
        cat2.setDescription("Books category");
        categories.add(cat2);

        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(categories);
        categoryResponse.setPageNo(pageNo);
        categoryResponse.setPageSize(pageSize);
        categoryResponse.setTotalElements(2L);
        categoryResponse.setTotalPages(1);
        categoryResponse.setLast(true);

        // Mock the service
        Mockito.when(categoryService.getAllCategories(pageNo, pageSize, sortBy, sortDir))
                .thenReturn(categoryResponse);

        // Act & Assert
        mockMvc.perform(get("/api/categories")
                        .param("pageNo", String.valueOf(pageNo))
                        .param("pageSize", String.valueOf(pageSize))
                        .param("sortBy", sortBy)
                        .param("sortDir", sortDir)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].name").value("Tech"))
                .andExpect(jsonPath("$.content[1].id").value(2))
                .andExpect(jsonPath("$.content[1].name").value("Books"))
                .andExpect(jsonPath("$.pageNo").value(pageNo))
                .andExpect(jsonPath("$.pageSize").value(pageSize))
                .andExpect(jsonPath("$.totalElements").value(2))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.last").value(true));
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
