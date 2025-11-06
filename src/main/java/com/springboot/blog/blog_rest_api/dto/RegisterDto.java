package com.springboot.blog.blog_rest_api.dto;


import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "Blog Register Name")

    private String name;

    @NotEmpty
    @Size(min = 5, message = "Username should have at least 5 characters")
    @Schema(description = "Blog Register Username")
    private String username;

    @NotEmpty(message = "Email should not be null or empty")
    @Email
    @Schema(description = "Blog Register Email")
    private String email;

    @NotEmpty
    @Size(min = 5, message = "Password should have at least 5 characters")
    @Schema(description = "Blog Register Password")
    private String password;
}