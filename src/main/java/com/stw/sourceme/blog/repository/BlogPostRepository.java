package com.stw.sourceme.blog.repository;

import com.stw.sourceme.blog.entity.BlogPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    /**
     * 블로그 포스트 검색 (제목, 요약, 내용, 태그 기준)
     * @param keyword 검색 키워드
     * @param pageable 페이징 정보
     * @return 검색된 블로그 포스트 페이지
     */
    @Query("SELECT DISTINCT bp FROM BlogPost bp " +
           "LEFT JOIN bp.tags t " +
           "WHERE (:keyword IS NULL OR :keyword = '' OR " +
           "LOWER(bp.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(bp.summary) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(bp.contentMarkdown) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(t.name) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<BlogPost> searchBlogPosts(@Param("keyword") String keyword, Pageable pageable);

    /**
     * 공개된 블로그 포스트만 검색
     * @param keyword 검색 키워드
     * @param pageable 페이징 정보
     * @return 검색된 공개 블로그 포스트 페이지
     */
    @Query("SELECT DISTINCT bp FROM BlogPost bp " +
           "LEFT JOIN bp.tags t " +
           "WHERE bp.status = 'PUBLISHED' AND " +
           "(:keyword IS NULL OR :keyword = '' OR " +
           "LOWER(bp.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(bp.summary) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(bp.contentMarkdown) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(t.name) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<BlogPost> searchPublishedBlogPosts(@Param("keyword") String keyword, Pageable pageable);

    /**
     * 인기 게시글 조회 (조회수 기준, 공개된 것만)
     */
    @Query("SELECT bp FROM BlogPost bp WHERE bp.status = 'PUBLISHED' ORDER BY bp.viewCount DESC, bp.publishedAt DESC")
    Page<BlogPost> findPopularPublishedPosts(Pageable pageable);

    /**
     * 최근 인기 게시글 조회 (좋아요 수 기준, 공개된 것만)
     */
    @Query("SELECT bp FROM BlogPost bp WHERE bp.status = 'PUBLISHED' ORDER BY bp.likeCount DESC, bp.publishedAt DESC")
    Page<BlogPost> findMostLikedPublishedPosts(Pageable pageable);
}
