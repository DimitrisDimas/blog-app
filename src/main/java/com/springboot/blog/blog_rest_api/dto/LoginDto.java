package com.springboot.blog.blog_rest_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "Login Username or Email")
    private String usernameOrEmail;
    @NotEmpty
    @Schema(description = "Login Password")
    @Size(min = 5, message = "Password should have at least 5 characters")
    private String password;
}