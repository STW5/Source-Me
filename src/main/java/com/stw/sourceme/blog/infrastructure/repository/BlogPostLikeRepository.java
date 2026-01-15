package com.stw.sourceme.blog.infrastructure.repository;

import com.stw.sourceme.blog.domain.BlogPostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BlogPostLikeRepository extends JpaRepository<BlogPostLike, BlogPostLike.BlogPostLikeId> {

    boolean existsByUserIdAndBlogPostId(Long userId, UUID blogPostId);

    void deleteByUserIdAndBlogPostId(Long userId, UUID blogPostId);

    long countByBlogPostId(UUID blogPostId);
}
