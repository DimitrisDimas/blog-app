package com.springboot.blog.blog_rest_api.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class RegisterDto {

    @NotEmpty
    @Size(min = 5, message = "Name should have at least 5 characters")
    private String name;
    @NotEmpty
    @Size(min = 5, message = "Username should have at least 5 characters")
    private String username;
    @NotEmpty(message = "Email should not be null or empty")
    @Email
    private String email;
    @NotEmpty
    @Size(min = 5, message = "Password should have at least 5 characters")
    private String password;
}