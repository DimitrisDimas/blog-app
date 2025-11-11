package com.springboot.blog.blog_rest_api.controller;

import com.springboot.blog.blog_rest_api.dto.JWTAuthResponse;
import com.springboot.blog.blog_rest_api.dto.LoginDto;
import com.springboot.blog.blog_rest_api.dto.RegisterDto;
import com.springboot.blog.blog_rest_api.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Tag( name = "REST APIs for Authentication Resource" )
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "Register REST API", description = "Register REST API is used to save users into database")
    @ApiResponse(responseCode = "201", description = "Http Status 201 CREATED")
    @PostMapping(value = {"/register","/signup"})
    public ResponseEntity<String> register(@Valid @RequestBody RegisterDto registerDto){
        String response = authService.register(registerDto);
        return new ResponseEntity(response, HttpStatus.CREATED);
    }


    @Operation(summary = "User Login API", description = "Authenticates a user using their username or email and password, returning an access token upon successful login.")
    @ApiResponse(responseCode = "200", description = "Http Status 200 SUCCESS")
    @PostMapping(value = {"/login","/signin"})
    public ResponseEntity<JWTAuthResponse> login(@Valid @RequestBody LoginDto loginDto){
        String token = authService.login(loginDto);

        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
        jwtAuthResponse.setAccessToken(token);
        return ResponseEntity.ok(jwtAuthResponse);
    }



}
