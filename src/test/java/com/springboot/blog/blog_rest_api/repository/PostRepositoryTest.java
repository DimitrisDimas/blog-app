package com.springboot.blog.blog_rest_api.repository;

import com.springboot.blog.blog_rest_api.entity.Category;
import com.springboot.blog.blog_rest_api.entity.Post;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class PostRepositoryTest {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void savePostWithCategory() {
        Category category = new Category();
        category.setName("Tech");
        category.setDescription("Tech posts");
        Category savedCategory = categoryRepository.save(category);

        Post post = new Post();
        post.setTitle("Test Post");
        post.setDescription("Description");
        post.setContent("Content");
        post.setCategory(savedCategory);
        Post savedPost = postRepository.save(post);

        assertThat(savedPost.getId()).isGreaterThan(0);
        assertThat(savedPost.getCategory().getId()).isEqualTo(savedCategory.getId());
    }
}

