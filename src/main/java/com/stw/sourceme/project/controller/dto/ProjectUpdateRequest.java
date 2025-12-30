package com.stw.sourceme.project.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class ProjectUpdateRequest {

    @NotBlank(message = "제목은 필수입니다.")
    @Size(max = 200, message = "제목은 200자 이하여야 합니다.")
    private String title;

    @NotBlank(message = "슬러그는 필수입니다.")
    @Size(max = 220, message = "슬러그는 220자 이하여야 합니다.")
    private String slug;

    @NotBlank(message = "요약은 필수입니다.")
    @Size(max = 300, message = "요약은 300자 이하여야 합니다.")
    private String summary;

    @NotBlank(message = "내용은 필수입니다.")
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
}
