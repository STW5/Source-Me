package com.stw.sourceme.project.repository;

import com.stw.sourceme.project.entity.Project;
import com.stw.sourceme.project.entity.ProjectBookmark;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectBookmarkRepository extends JpaRepository<ProjectBookmark, ProjectBookmark.ProjectBookmarkId> {

    boolean existsByUserIdAndProjectId(Long userId, Long projectId);

    void deleteByUserIdAndProjectId(Long userId, Long projectId);

    @Query("SELECT b.project FROM ProjectBookmark b WHERE b.userId = :userId ORDER BY b.createdAt DESC")
    Page<Project> findBookmarkedProjectsByUserId(@Param("userId") Long userId, Pageable pageable);
}
