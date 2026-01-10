package com.stw.sourceme.blog.repository;

import com.stw.sourceme.blog.entity.BlogPost;
import com.stw.sourceme.blog.entity.BlogPostBookmark;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BlogPostBookmarkRepository extends JpaRepository<BlogPostBookmark, BlogPostBookmark.BlogPostBookmarkId> {

    boolean existsByUserIdAndBlogPostId(Long userId, UUID blogPostId);

    void deleteByUserIdAndBlogPostId(Long userId, UUID blogPostId);

    @Query("SELECT b.blogPost FROM BlogPostBookmark b WHERE b.userId = :userId ORDER BY b.createdAt DESC")
    Page<BlogPost> findBookmarkedPostsByUserId(@Param("userId") Long userId, Pageable pageable);
}
