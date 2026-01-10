package com.stw.sourceme.project.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stw.sourceme.common.exception.BusinessException;
import com.stw.sourceme.common.exception.ErrorCode;
import com.stw.sourceme.common.exception.ResourceNotFoundException;
import com.stw.sourceme.media.entity.MediaFile;
import com.stw.sourceme.media.repository.MediaFileRepository;
import com.stw.sourceme.project.controller.dto.ProjectCreateRequest;
import com.stw.sourceme.project.controller.dto.ProjectListResponse;
import com.stw.sourceme.project.controller.dto.ProjectResponse;
import com.stw.sourceme.project.controller.dto.ProjectUpdateRequest;
import com.stw.sourceme.project.entity.Project;
import com.stw.sourceme.project.repository.ProjectRepository;
import com.stw.sourceme.tag.entity.Tag;
import com.stw.sourceme.tag.repository.TagRepository;
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final TagRepository tagRepository;
    private final MediaFileRepository mediaFileRepository;

    public List<ProjectListResponse> getAllProjects() {
        return projectRepository.findAll().stream()
                .filter(Project::getIsPublished)
                .map(ProjectListResponse::from)
                .collect(Collectors.toList());
    }

    /**
     * 프로젝트 검색 (페이징 지원)
     * @param keyword 검색 키워드 (null 또는 빈 문자열이면 전체 조회)
     * @param pageable 페이징 정보
     * @return 검색된 공개 프로젝트 페이지
     */
    public Page<ProjectListResponse> searchPublishedProjects(String keyword, Pageable pageable) {
        Page<Project> projectPage = projectRepository.searchPublishedProjects(keyword, pageable);
        return projectPage.map(ProjectListResponse::from);
    }

    /**
     * 모든 프로젝트 검색 (관리자용, 페이징 지원)
     * @param keyword 검색 키워드 (null 또는 빈 문자열이면 전체 조회)
     * @param pageable 페이징 정보
     * @return 검색된 모든 프로젝트 페이지
     */
    public Page<ProjectListResponse> searchAllProjects(String keyword, Pageable pageable) {
        Page<Project> projectPage = projectRepository.searchProjects(keyword, pageable);
        return projectPage.map(ProjectListResponse::from);
    }

    public ProjectResponse getProjectById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.PROJECT_NOT_FOUND));
        return ProjectResponse.from(project);
    }

    // 조회수 증가
    @Transactional
    public void incrementViewCount(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.PROJECT_NOT_FOUND));
        project.incrementViewCount();
    }

    // 인기 프로젝트 조회 (조회수 기준)
    public Page<ProjectListResponse> getPopularProjects(Pageable pageable) {
        Page<Project> projectPage = projectRepository.findPopularPublishedProjects(pageable);
        return projectPage.map(ProjectListResponse::from);
    }

    // 좋아요가 많은 프로젝트 조회
    public Page<ProjectListResponse> getMostLikedProjects(Pageable pageable) {
        Page<Project> projectPage = projectRepository.findMostLikedPublishedProjects(pageable);
        return projectPage.map(ProjectListResponse::from);
    }

    @Transactional
    public ProjectResponse createProject(ProjectCreateRequest request) {
        if (projectRepository.existsBySlug(request.getSlug())) {
            throw new BusinessException(ErrorCode.PROJECT_SLUG_ALREADY_EXISTS);
        }

        Project project = request.toEntity();
        project.update(
                project.getTitle(),
                project.getSlug(),
                project.getSummary(),
                project.getContentMarkdown(),
                project.getStartedAt(),
                project.getEndedAt(),
                project.getIsPublished(),
                project.getIsFeatured(),
                project.getFeaturedOrder(),
                project.getGithubUrl(),
                project.getDemoUrl(),
                resolveThumbnailMedia(request.getThumbnailMediaId())
        );
        if (request.getTagNames() != null && !request.getTagNames().isEmpty()) {
            List<Tag> tags = getOrCreateTags(request.getTagNames());
            project.updateTags(tags);
        }

        project = projectRepository.save(project);
        return ProjectResponse.from(project);
    }

    @Transactional
    public ProjectResponse updateProject(Long id, ProjectUpdateRequest request) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.PROJECT_NOT_FOUND));

        // Check slug uniqueness (except current project)
        if (!project.getSlug().equals(request.getSlug()) &&
            projectRepository.existsBySlug(request.getSlug())) {
            throw new BusinessException(ErrorCode.PROJECT_SLUG_ALREADY_EXISTS);
        }

        project.update(
                request.getTitle(),
                request.getSlug(),
                request.getSummary(),
                request.getContentMarkdown(),
                request.getStartedAt(),
                request.getEndedAt(),
                request.getIsPublished(),
                request.getIsFeatured(),
                request.getFeaturedOrder() != null ? request.getFeaturedOrder() : 0,
                request.getGithubUrl(),
                request.getDemoUrl(),
                resolveThumbnailMedia(request.getThumbnailMediaId())
        );

        if (request.getTagNames() != null) {
            List<Tag> tags = getOrCreateTags(request.getTagNames());
            project.updateTags(tags);
        }

        return ProjectResponse.from(project);
    }

    @Transactional
    public void deleteProject(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.PROJECT_NOT_FOUND));
        projectRepository.delete(project);
    }

    private List<Tag> getOrCreateTags(List<String> tagNames) {
        List<Tag> tags = new ArrayList<>();
        for (String tagName : tagNames) {
            String trimmedName = tagName.trim();
            if (!trimmedName.isEmpty()) {
                Tag tag = tagRepository.findByName(trimmedName)
                        .orElseGet(() -> tagRepository.save(Tag.builder().name(trimmedName).build()));
                tags.add(tag);
            }
        }
        return tags;
    }

    private MediaFile resolveThumbnailMedia(Long thumbnailMediaId) {
        if (thumbnailMediaId == null) {
            return null;
        }
        return mediaFileRepository.findById(thumbnailMediaId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEDIA_NOT_FOUND));
    }
}
