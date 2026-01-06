package com.stw.sourceme.project.controller.dto;

import com.stw.sourceme.project.entity.Project;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class ProjectCreateRequest {

    @NotBlank(message = "제목은 필수입니다.")
    @Size(max = 200, message = "제목은 200자 이하여야 합니다.")
    private String title;

    @NotBlank(message = "슬러그는 필수입니다.")
    @Size(max = 220, message = "슬러그는 220자 이하여야 합니다.")
    private String slug;

    @NotBlank(message = "요약은 필수입니다.")
    @Size(max = 300, message = "요약은 300자 이하여야 합니다.")
    private String summary;

    private String contentMarkdown;

    private LocalDate startedAt;
    private LocalDate endedAt;

    @NotNull(message = "공개 여부는 필수입니다.")
    private Boolean isPublished;

    @NotNull(message = "특집 여부는 필수입니다.")
    private Boolean isFeatured;

    private Integer featuredOrder;

    @Size(max = 255, message = "GitHub URL은 255자 이하여야 합니다.")
    private String githubUrl;

    @Size(max = 255, message = "Demo URL은 255자 이하여야 합니다.")
    private String demoUrl;

    private List<String> tagNames = new ArrayList<>();

    public Project toEntity() {
        return Project.builder()
                .title(title)
                .slug(slug)
                .summary(summary)
                .contentMarkdown(contentMarkdown)
                .startedAt(startedAt)
                .endedAt(endedAt)
                .isPublished(isPublished)
                .isFeatured(isFeatured)
                .featuredOrder(featuredOrder != null ? featuredOrder : 0)
                .githubUrl(githubUrl)
                .demoUrl(demoUrl)
                .build();
    }
}
