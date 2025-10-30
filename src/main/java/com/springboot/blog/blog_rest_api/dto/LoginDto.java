package com.springboot.blog.blog_rest_api.dto;

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

public class LoginDto {

    @NotEmpty
    @Size(min = 5, message = "Username or email should have at least 5 characters")
    private String usernameOrEmail;
    @NotEmpty
    @Size(min = 5, message = "Password should have at least 5 characters")
    private String password;
}