package com.stw.sourceme.project.controller.dto;

import com.stw.sourceme.project.entity.Project;
import com.stw.sourceme.tag.controller.dto.TagResponse;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class ProjectResponse {
    private Long id;
    private String title;
    private String slug;
    private String summary;
    private String contentMarkdown;
    private LocalDate startedAt;
    private LocalDate endedAt;
    private Boolean isPublished;
    private Boolean isFeatured;
    private Integer featuredOrder;
    private String githubUrl;
    private String demoUrl;
    private List<TagResponse> tags;

    public static ProjectResponse from(Project project) {
        return ProjectResponse.builder()
                .id(project.getId())
                .title(project.getTitle())
                .slug(project.getSlug())
                .summary(project.getSummary())
                .contentMarkdown(project.getContentMarkdown())
                .startedAt(project.getStartedAt())
                .endedAt(project.getEndedAt())
                .isPublished(project.getIsPublished())
                .isFeatured(project.getIsFeatured())
                .featuredOrder(project.getFeaturedOrder())
                .githubUrl(project.getGithubUrl())
                .demoUrl(project.getDemoUrl())
                .tags(project.getTags().stream()
                        .map(TagResponse::from)
                        .collect(Collectors.toList()))
                .build();
    }
}
