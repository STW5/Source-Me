package com.stw.sourceme.project.service;

import com.stw.sourceme.common.exception.ErrorCode;
import com.stw.sourceme.common.exception.ResourceNotFoundException;
import com.stw.sourceme.project.entity.Project;
import com.stw.sourceme.project.entity.ProjectLike;
import com.stw.sourceme.project.repository.ProjectLikeRepository;
import com.stw.sourceme.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectLikeService {

    private final ProjectLikeRepository projectLikeRepository;
    private final ProjectRepository projectRepository;

    /**
     * 좋아요 추가/제거 토글
     * @param userId 사용자 ID
     * @param projectId 프로젝트 ID
     * @return true: 좋아요 추가됨, false: 좋아요 제거됨
     */
    @Transactional
    public boolean toggleLike(Long userId, Long projectId) {
        // 프로젝트 존재 확인
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.PROJECT_NOT_FOUND));

        // 이미 좋아요를 눌렀는지 확인
        if (projectLikeRepository.existsByUserIdAndProjectId(userId, projectId)) {
            // 좋아요 제거
            projectLikeRepository.deleteByUserIdAndProjectId(userId, projectId);
            project.decrementLikeCount();
            return false;
        } else {
            // 좋아요 추가
            ProjectLike like = ProjectLike.builder()
                    .userId(userId)
                    .projectId(projectId)
                    .build();
            projectLikeRepository.save(like);
            project.incrementLikeCount();
            return true;
        }
    }

    /**
     * 특정 사용자가 프로젝트에 좋아요를 눌렀는지 확인
     */
    public boolean isLiked(Long userId, Long projectId) {
        return projectLikeRepository.existsByUserIdAndProjectId(userId, projectId);
    }

    /**
     * 프로젝트의 좋아요 수 조회
     */
    public long getLikeCount(Long projectId) {
        return projectLikeRepository.countByProjectId(projectId);
    }
}
