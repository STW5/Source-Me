package com.stw.sourceme.blog.controller.dto;

import com.stw.sourceme.blog.entity.BlogPost;
import com.stw.sourceme.tag.controller.dto.TagResponse;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Builder
public class BlogPostResponse {
    private UUID id;
    private String title;
    private String summary;
    private String contentMarkdown;
    private String status;
    private OffsetDateTime publishedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long viewCount;
    private Long likeCount;
    private List<TagResponse> tags;

    public static BlogPostResponse from(BlogPost blogPost) {
        return BlogPostResponse.builder()
                .id(blogPost.getId())
                .title(blogPost.getTitle())
                .summary(blogPost.getSummary())
                .contentMarkdown(blogPost.getContentMarkdown())
                .status(blogPost.getStatus())
                .publishedAt(blogPost.getPublishedAt())
                .createdAt(blogPost.getCreatedAt())
                .updatedAt(blogPost.getUpdatedAt())
                .viewCount(blogPost.getViewCount())
                .likeCount(blogPost.getLikeCount())
                .tags(blogPost.getTags().stream()
                        .map(TagResponse::from)
                        .collect(Collectors.toList()))
                .build();
    }
}
