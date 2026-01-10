package com.stw.sourceme.project.service;

import com.stw.sourceme.common.exception.ErrorCode;
import com.stw.sourceme.common.exception.ResourceNotFoundException;
import com.stw.sourceme.project.controller.dto.ProjectListResponse;
import com.stw.sourceme.project.entity.Project;
import com.stw.sourceme.project.entity.ProjectBookmark;
import com.stw.sourceme.project.repository.ProjectBookmarkRepository;
import com.stw.sourceme.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectBookmarkService {

    private final ProjectBookmarkRepository projectBookmarkRepository;
    private final ProjectRepository projectRepository;

    /**
     * 북마크 추가/제거 토글
     * @param userId 사용자 ID
     * @param projectId 프로젝트 ID
     * @return true: 북마크 추가됨, false: 북마크 제거됨
     */
    @Transactional
    public boolean toggleBookmark(Long userId, Long projectId) {
        // 프로젝트 존재 확인
        projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.PROJECT_NOT_FOUND));

        // 이미 북마크했는지 확인
        if (projectBookmarkRepository.existsByUserIdAndProjectId(userId, projectId)) {
            // 북마크 제거
            projectBookmarkRepository.deleteByUserIdAndProjectId(userId, projectId);
            return false;
        } else {
            // 북마크 추가
            ProjectBookmark bookmark = ProjectBookmark.builder()
                    .userId(userId)
                    .projectId(projectId)
                    .build();
            projectBookmarkRepository.save(bookmark);
            return true;
        }
    }

    /**
     * 특정 사용자가 프로젝트를 북마크했는지 확인
     */
    public boolean isBookmarked(Long userId, Long projectId) {
        return projectBookmarkRepository.existsByUserIdAndProjectId(userId, projectId);
    }

    /**
     * 사용자의 북마크 목록 조회
     */
    public Page<ProjectListResponse> getBookmarkedProjects(Long userId, Pageable pageable) {
        Page<Project> bookmarkedProjects = projectBookmarkRepository.findBookmarkedProjectsByUserId(userId, pageable);
        return bookmarkedProjects.map(ProjectListResponse::from);
    }
}
