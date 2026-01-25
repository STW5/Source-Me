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

    public Page<ProjectListResponse> getAllProjects(Pageable pageable) {
        return projectRepository.findAllPublishedProjects(pageable)
                .map(ProjectListResponse::from);
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

    @Transactional
    public ProjectResponse createProject(ProjectCreateRequest request) {
        validateSlugUniqueness(request.getSlug(), null);

        Project project = buildProjectFromRequest(request);
        updateProjectTags(project, request.getTagNames());

        project = projectRepository.save(project);
        return ProjectResponse.from(project);
    }

    @Transactional
    public ProjectResponse updateProject(Long id, ProjectUpdateRequest request) {
        Project project = findProjectById(id);
        validateSlugUniqueness(request.getSlug(), project.getSlug());

        updateProjectFromRequest(project, request);
        updateProjectTags(project, request.getTagNames());

        return ProjectResponse.from(project);
    }

    @Transactional
    public void deleteProject(Long id) {
        Project project = findProjectById(id);
        projectRepository.delete(project);
    }

    private Project findProjectById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.PROJECT_NOT_FOUND));
    }

    private void validateSlugUniqueness(String newSlug, String currentSlug) {
        if (currentSlug == null) {
            if (projectRepository.existsBySlug(newSlug)) {
                throw new BusinessException(ErrorCode.PROJECT_SLUG_ALREADY_EXISTS);
            }
        } else if (!currentSlug.equals(newSlug) && projectRepository.existsBySlug(newSlug)) {
            throw new BusinessException(ErrorCode.PROJECT_SLUG_ALREADY_EXISTS);
        }
    }

    private Project buildProjectFromRequest(ProjectCreateRequest request) {
        Project project = request.toEntity();
        MediaFile thumbnailMedia = resolveThumbnailMedia(request.getThumbnailMediaId());

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
                project.getTeamSize(),
                project.getRole(),
                project.getOwnedServices(),
                project.getIntroductionMarkdown(),
                project.getResponsibilitiesMarkdown(),
                project.getTroubleshootingMarkdown(),
                thumbnailMedia
        );

        return project;
    }

    private void updateProjectFromRequest(Project project, ProjectUpdateRequest request) {
        MediaFile thumbnailMedia = resolveThumbnailMedia(request.getThumbnailMediaId());
        Integer featuredOrder = request.getFeaturedOrder() != null ? request.getFeaturedOrder() : 0;

        project.update(
                request.getTitle(),
                request.getSlug(),
                request.getSummary(),
                request.getContentMarkdown(),
                request.getStartedAt(),
                request.getEndedAt(),
                request.getIsPublished(),
                request.getIsFeatured(),
                featuredOrder,
                request.getGithubUrl(),
                request.getDemoUrl(),
                request.getTeamSize(),
                request.getRole(),
                request.getOwnedServices(),
                request.getIntroductionMarkdown(),
                request.getResponsibilitiesMarkdown(),
                request.getTroubleshootingMarkdown(),
                thumbnailMedia
        );
    }

    private void updateProjectTags(Project project, List<String> tagNames) {
        if (tagNames != null && !tagNames.isEmpty()) {
            List<Tag> tags = getOrCreateTags(tagNames);
            project.updateTags(tags);
        }
    }

    private List<Tag> getOrCreateTags(List<String> tagNames) {
        return tagNames.stream()
                .map(String::trim)
                .filter(name -> !name.isEmpty())
                .map(this::getOrCreateTag)
                .collect(Collectors.toList());
    }

    private Tag getOrCreateTag(String tagName) {
        return tagRepository.findByName(tagName)
                .orElseGet(() -> tagRepository.save(Tag.builder().name(tagName).build()));
    }

    private MediaFile resolveThumbnailMedia(Long thumbnailMediaId) {
        if (thumbnailMediaId == null) {
            return null;
        }
        return mediaFileRepository.findById(thumbnailMediaId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEDIA_NOT_FOUND));
    }
}
