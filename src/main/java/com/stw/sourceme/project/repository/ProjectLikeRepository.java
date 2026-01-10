package com.stw.sourceme.project.repository;

import com.stw.sourceme.project.entity.ProjectLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectLikeRepository extends JpaRepository<ProjectLike, ProjectLike.ProjectLikeId> {

    boolean existsByUserIdAndProjectId(Long userId, Long projectId);

    void deleteByUserIdAndProjectId(Long userId, Long projectId);

    long countByProjectId(Long projectId);
}
