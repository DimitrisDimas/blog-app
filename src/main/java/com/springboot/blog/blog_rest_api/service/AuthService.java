package com.springboot.blog.blog_rest_api.service;

import com.springboot.blog.blog_rest_api.dto.RegisterDto;

public interface AuthService {
    String register(RegisterDto registerDto);
}
