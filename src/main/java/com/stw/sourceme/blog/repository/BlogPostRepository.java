package com.stw.sourceme.blog.repository;

import com.stw.sourceme.blog.entity.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BlogPostRepository extends JpaRepository<BlogPost, UUID> {

    @Query("SELECT DISTINCT bp FROM BlogPost bp JOIN bp.tags t WHERE t.name = :tagName")
    List<BlogPost> findByTagName(@Param("tagName") String tagName);
}
