package com.springboot.blog.blog_rest_api.repository;

import com.springboot.blog.blog_rest_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
