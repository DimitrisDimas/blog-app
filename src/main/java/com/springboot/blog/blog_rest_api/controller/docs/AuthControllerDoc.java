package com.springboot.blog.blog_rest_api.controller.docs;

import com.springboot.blog.blog_rest_api.dto.JWTAuthResponse;
import com.springboot.blog.blog_rest_api.dto.LoginDto;
import com.springboot.blog.blog_rest_api.dto.RegisterDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Authentication", description = "REST APIs for user authentication and registration")
public interface AuthControllerDoc {

    @Operation(
            summary = "Register a new user",
            description = "Creates a new user account using the provided registration details.",
            tags = {"Authentication"},
            responses = {
                    @ApiResponse(description = "Created", responseCode = "201", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Conflict", responseCode = "409", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            }
    )
    ResponseEntity<String> register(RegisterDto registerDto);

    @Operation(
            summary = "Authenticate user and return JWT token",
            description = "Validates user credentials and returns a JWT access token for further authenticated requests.",
            tags = {"Authentication"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            }
    )
    ResponseEntity<JWTAuthResponse> login(LoginDto loginDto);
}
