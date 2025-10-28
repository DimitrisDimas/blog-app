package com.springboot.blog.blog_rest_api.repository;


import com.springboot.blog.blog_rest_api.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
