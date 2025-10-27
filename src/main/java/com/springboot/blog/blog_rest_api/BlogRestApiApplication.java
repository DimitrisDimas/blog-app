package com.springboot.blog.blog_rest_api;

import com.springboot.blog.blog_rest_api.entity.Role;
import com.springboot.blog.blog_rest_api.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BlogRestApiApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(BlogRestApiApplication.class, args);
	}


    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        Role adminRole = new Role();
        adminRole.setName("ROLE_ADMIN");
        roleRepository.save(adminRole);

        Role userRole = new Role();
        userRole.setName("ROLE_USER");
        roleRepository.save(userRole);
    }

}
