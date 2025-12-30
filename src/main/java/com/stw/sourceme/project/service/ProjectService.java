package com.stw.sourceme.project.service;

import com.stw.sourceme.common.exception.BusinessException;
import com.stw.sourceme.common.exception.ErrorCode;
import com.stw.sourceme.common.exception.ResourceNotFoundException;
import com.stw.sourceme.project.controller.dto.ProjectCreateRequest;
import com.stw.sourceme.project.controller.dto.ProjectListResponse;
import com.stw.sourceme.project.controller.dto.ProjectResponse;
import com.stw.sourceme.project.controller.dto.ProjectUpdateRequest;
import com.stw.sourceme.project.entity.Project;
import com.stw.sourceme.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectService {

    private final ProjectRepository projectRepository;

    public List<ProjectListResponse> getAllProjects() {
        return projectRepository.findAll().stream()
                .filter(Project::getIsPublished)
                .map(ProjectListResponse::from)
                .collect(Collectors.toList());
    }

    public ProjectResponse getProjectById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.PROJECT_NOT_FOUND));
        return ProjectResponse.from(project);
    }

    @Transactional
    public ProjectResponse createProject(ProjectCreateRequest request) {
        if (projectRepository.existsBySlug(request.getSlug())) {
            throw new BusinessException(ErrorCode.PROJECT_SLUG_ALREADY_EXISTS);
        }

        Project project = projectRepository.save(request.toEntity());
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
                request.getDemoUrl()
        );

        return ProjectResponse.from(project);
    }

    @Transactional
    public void deleteProject(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.PROJECT_NOT_FOUND));
        projectRepository.delete(project);
    }
}
