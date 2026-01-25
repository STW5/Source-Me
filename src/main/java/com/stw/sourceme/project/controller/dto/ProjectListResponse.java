package com.stw.sourceme.project.controller.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Builder;
import lombok.Getter;

import com.stw.sourceme.media.controller.dto.MediaFileResponse;
import com.stw.sourceme.project.entity.Project;
import com.stw.sourceme.tag.controller.dto.TagResponse;
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
    private MediaFileResponse thumbnailMedia;
    private List<TagResponse> tags;

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
                .thumbnailMedia(MediaFileResponse.from(project.getThumbnailMedia()))
                .tags(project.getTags().stream()
                        .map(TagResponse::from)
                        .collect(Collectors.toList()))
                .build();
    }
}
