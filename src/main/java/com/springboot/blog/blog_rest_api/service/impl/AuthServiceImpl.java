package com.springboot.blog.blog_rest_api.service.impl;

import com.springboot.blog.blog_rest_api.repository.RoleRepository;
import com.springboot.blog.blog_rest_api.repository.UserRepository;
import com.springboot.blog.blog_rest_api.service.AuthService;

public class AuthServiceImpl implements AuthService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;

    public AuthServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }
}
