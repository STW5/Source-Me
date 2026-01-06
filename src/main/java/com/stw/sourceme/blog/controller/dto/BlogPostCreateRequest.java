package com.stw.sourceme.blog.controller.dto;

import com.stw.sourceme.blog.entity.BlogPost;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class BlogPostCreateRequest {

    @NotBlank(message = "제목은 필수입니다.")
    @Size(max = 200, message = "제목은 200자 이하여야 합니다.")
    private String title;

    @Size(max = 300, message = "요약은 300자 이하여야 합니다.")
    private String summary;

    @NotBlank(message = "본문은 필수입니다.")
    private String contentMarkdown;

    @NotBlank(message = "상태는 필수입니다.")
    @Size(max = 20, message = "상태는 20자 이하여야 합니다.")
    private String status; // DRAFT or PUBLISHED

    private List<String> tagNames = new ArrayList<>();

    public BlogPost toEntity() {
        return BlogPost.builder()
                .title(title)
                .summary(summary)
                .contentMarkdown(contentMarkdown)
                .status(status)
                .publishedAt("PUBLISHED".equals(status) ? java.time.OffsetDateTime.now() : null)
                .build();
    }
}
