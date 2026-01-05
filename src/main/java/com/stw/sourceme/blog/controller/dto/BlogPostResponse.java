package com.stw.sourceme.blog.controller.dto;

import com.stw.sourceme.blog.entity.BlogPost;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Getter
@Builder
public class BlogPostResponse {
    private Long id;
    private String title;
    private String slug;
    private String summary;
    private String contentMarkdown;
    private String status;
    private OffsetDateTime publishedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static BlogPostResponse from(BlogPost blogPost) {
        return BlogPostResponse.builder()
                .id(blogPost.getId())
                .title(blogPost.getTitle())
                .slug(blogPost.getSlug())
                .summary(blogPost.getSummary())
                .contentMarkdown(blogPost.getContentMarkdown())
                .status(blogPost.getStatus())
                .publishedAt(blogPost.getPublishedAt())
                .createdAt(blogPost.getCreatedAt())
                .updatedAt(blogPost.getUpdatedAt())
                .build();
    }
}
