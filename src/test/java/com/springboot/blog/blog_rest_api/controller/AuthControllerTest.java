package com.springboot.blog.blog_rest_api.controller;

import com.springboot.blog.blog_rest_api.dto.JWTAuthResponse;
import com.springboot.blog.blog_rest_api.dto.LoginDto;
import com.springboot.blog.blog_rest_api.entity.Role;
import com.springboot.blog.blog_rest_api.entity.User;
import com.springboot.blog.blog_rest_api.repository.RoleRepository;
import com.springboot.blog.blog_rest_api.repository.UserRepository;
import io.restassured.RestAssured;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuthControllerJsonTest {

    private JWTAuthResponse tokenDto;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        // Ρυθμίσεις RestAssured
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
        RestAssured.basePath = "/auth";

        // Δημιουργούμε ρόλο αν δεν υπάρχει
        if (roleRepository.findByName("ROLE_USER").isEmpty()) {
            Role role = new Role();
            role.setName("ROLE_USER");
            roleRepository.save(role);
        }

        // Δημιουργούμε user αν δεν υπάρχει
        if (userRepository.findByUsername("Masatos7").isEmpty()) {
            User user = new User();
            user.setUsername("Masatos7");
            user.setName("Masatos7");
            user.setEmail("masatos7@example.com");
            user.setPassword(passwordEncoder.encode("Masatos7"));
            user.setRoles(Set.of(roleRepository.findByName("ROLE_USER").get()));
            userRepository.save(user);
        }
    }

    @Test
    void example(){

    }

    /*
    @Test
    @Order(1)
    void signin() {
        LoginDto credentials = new LoginDto("Masatos7", "Masatos7");

        tokenDto = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(credentials)
                .when()
                .post("/signin") // ή /login
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(JWTAuthResponse.class);

        System.out.println("Token: " + tokenDto.getAccessToken());
        assertNotNull(tokenDto.getAccessToken());
    }
    */

}
