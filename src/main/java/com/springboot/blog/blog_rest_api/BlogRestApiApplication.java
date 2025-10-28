package com.springboot.blog.blog_rest_api;

import com.springboot.blog.blog_rest_api.entity.Role;
import com.springboot.blog.blog_rest_api.repository.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BlogRestApiApplication implements CommandLineRunner {

    @Bean
    public ModelMapper modelMapper(){return new ModelMapper();}


    public static void main(String[] args) {
        SpringApplication.run(BlogRestApiApplication.class, args);
    }


    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        addRoleIfNotExists("ROLE_ADMIN");
        addRoleIfNotExists("ROLE_USER");
    }

    private void addRoleIfNotExists(String roleName) {
        if (!roleRepository.existsByName(roleName)) {
            Role role = new Role();
            role.setName(roleName);
            roleRepository.save(role);
        }
    }

}