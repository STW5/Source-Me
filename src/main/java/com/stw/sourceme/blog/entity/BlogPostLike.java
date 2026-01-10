package com.stw.sourceme.blog.entity;

import com.stw.sourceme.auth.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "blog_post_like")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@IdClass(BlogPostLike.BlogPostLikeId.class)
public class BlogPostLike {

    @Id
    @Column(name = "user_id")
    private Long userId;

    @Id
    @Column(name = "blog_post_id")
    private UUID blogPostId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blog_post_id", insertable = false, updatable = false)
    private BlogPost blogPost;

    @Column(name = "created_at", nullable = false)
    @Builder.Default
    private OffsetDateTime createdAt = OffsetDateTime.now();

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BlogPostLikeId implements Serializable {
        private Long userId;
        private UUID blogPostId;
    }
}
