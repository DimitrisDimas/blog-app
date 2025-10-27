package com.springboot.blog.blog_rest_api.service.impl;

import com.springboot.blog.blog_rest_api.dto.RegisterDto;
import com.springboot.blog.blog_rest_api.entity.Role;
import com.springboot.blog.blog_rest_api.entity.User;
import com.springboot.blog.blog_rest_api.repository.RoleRepository;
import com.springboot.blog.blog_rest_api.repository.UserRepository;
import com.springboot.blog.blog_rest_api.service.AuthService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;

    public AuthServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public String register(RegisterDto registerDto) {


        //TODO REFACTOR (MODEL MAPPER)
        User user = new User();
        user.setName(registerDto.getName());
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(registerDto.getPassword());

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER").get();
        roles.add(userRole);
        user.setRoles(roles);

        userRepository.save(user);

        return "User registered successfully";
    }
}
