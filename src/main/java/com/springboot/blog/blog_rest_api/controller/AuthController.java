package com.springboot.blog.blog_rest_api.controller;

import com.springboot.blog.blog_rest_api.controller.docs.AuthControllerDoc;
import com.springboot.blog.blog_rest_api.dto.JWTAuthResponse;
import com.springboot.blog.blog_rest_api.dto.LoginDto;
import com.springboot.blog.blog_rest_api.dto.RegisterDto;
import com.springboot.blog.blog_rest_api.entity.User;
import com.springboot.blog.blog_rest_api.repository.UserRepository;
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
public class AuthController implements AuthControllerDoc {

    private AuthService authService;
    private UserRepository userRepository;

    public AuthController(AuthService authService, UserRepository userRepository) {
        this.authService = authService;
        this.userRepository = userRepository;
    }

    @PostMapping(value = {"/register","/signup"})
    public ResponseEntity<String> register(@Valid @RequestBody RegisterDto registerDto){
        String response = authService.register(registerDto);
        return new ResponseEntity(response, HttpStatus.CREATED);
    }



    @PostMapping(value = {"/login","/signin"})
    public ResponseEntity<JWTAuthResponse> login(@Valid @RequestBody LoginDto loginDto){
        String token = authService.login(loginDto);
        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
        jwtAuthResponse.setAccessToken(token);

        String name = userRepository.getNameByUsername(loginDto.getUsername());


        jwtAuthResponse.setName(name);

        return ResponseEntity.ok(jwtAuthResponse);
    }



}
