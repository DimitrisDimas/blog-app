package com.springboot.blog.blog_rest_api.repository;

import com.springboot.blog.blog_rest_api.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PostRepository extends JpaRepository<Post,Long> {

    List<Post> findByCategoryId(long categoryId);
    Page<Post> findByCategoryId(long categoryId, Pageable pageable);

}
