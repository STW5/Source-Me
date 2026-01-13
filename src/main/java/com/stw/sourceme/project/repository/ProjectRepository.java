package com.stw.sourceme.project.repository;

import com.stw.sourceme.project.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    Optional<Project> findBySlug(String slug);

    boolean existsBySlug(String slug);

    /**
     * 공개된 프로젝트 조회 (페이징)
     */
    @Query("SELECT p FROM Project p WHERE p.isPublished = true ORDER BY p.createdAt DESC")
    Page<Project> findAllPublishedProjects(Pageable pageable);

    /**
     * 프로젝트 검색 (제목, 요약, 태그 기준)
     * @param keyword 검색 키워드
     * @param pageable 페이징 정보
     * @return 검색된 프로젝트 페이지
     */
    @Query("SELECT DISTINCT p FROM Project p " +
           "LEFT JOIN p.tags t " +
           "WHERE (:keyword IS NULL OR :keyword = '' OR " +
           "LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(p.summary) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(t.name) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<Project> searchProjects(@Param("keyword") String keyword, Pageable pageable);

    /**
     * 공개된 프로젝트만 검색
     * @param keyword 검색 키워드
     * @param pageable 페이징 정보
     * @return 검색된 공개 프로젝트 페이지
     */
    @Query("SELECT DISTINCT p FROM Project p " +
           "LEFT JOIN p.tags t " +
           "WHERE p.isPublished = true AND " +
           "(:keyword IS NULL OR :keyword = '' OR " +
           "LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(p.summary) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(t.name) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<Project> searchPublishedProjects(@Param("keyword") String keyword, Pageable pageable);

    /**
     * 인기 프로젝트 조회 (조회수 기준, 공개된 것만)
     */
    @Query("SELECT p FROM Project p WHERE p.isPublished = true ORDER BY p.viewCount DESC, p.createdAt DESC")
    Page<Project> findPopularPublishedProjects(Pageable pageable);

    /**
     * 좋아요가 많은 프로젝트 조회 (공개된 것만)
     */
    @Query("SELECT p FROM Project p WHERE p.isPublished = true ORDER BY p.likeCount DESC, p.createdAt DESC")
    Page<Project> findMostLikedPublishedProjects(Pageable pageable);
}
