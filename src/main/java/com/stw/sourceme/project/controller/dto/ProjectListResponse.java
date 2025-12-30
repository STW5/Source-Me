package com.stw.sourceme.project.controller.dto;

import com.stw.sourceme.project.entity.Project;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class ProjectListResponse {
    private Long id;
    private String title;
    private String slug;
    private String summary;
    private LocalDate startedAt;
    private LocalDate endedAt;
    private Boolean isFeatured;
    private String githubUrl;
    private String demoUrl;

    public static ProjectListResponse from(Project project) {
        return ProjectListResponse.builder()
                .id(project.getId())
                .title(project.getTitle())
                .slug(project.getSlug())
                .summary(project.getSummary())
                .startedAt(project.getStartedAt())
                .endedAt(project.getEndedAt())
                .isFeatured(project.getIsFeatured())
                .githubUrl(project.getGithubUrl())
                .demoUrl(project.getDemoUrl())
                .build();
    }
}
