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
public class BlogPostListResponse {
    private UUID id;
    private String title;
    private String summary;
    private String status;
    private OffsetDateTime publishedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long viewCount;
    private Long likeCount;
    private List<TagResponse> tags;

    public static BlogPostListResponse from(BlogPost blogPost) {
        return BlogPostListResponse.builder()
                .id(blogPost.getId())
                .title(blogPost.getTitle())
                .summary(blogPost.getSummary())
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
