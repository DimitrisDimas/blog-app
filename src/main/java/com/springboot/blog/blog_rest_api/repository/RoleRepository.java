package com.springboot.blog.blog_rest_api.repository;

import com.springboot.blog.blog_rest_api.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {
}
