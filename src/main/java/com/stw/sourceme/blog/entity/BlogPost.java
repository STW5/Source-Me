package com.stw.sourceme.blog.entity;

import com.stw.sourceme.common.BaseEntity;
import com.stw.sourceme.media.entity.MediaFile;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "blog_post")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class BlogPost extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, unique = true, length = 220)
    private String slug;

    @Column(length = 300)
    private String summary;

    @Column(name = "content_markdown", nullable = false, columnDefinition = "TEXT")
    private String contentMarkdown;

    @Column(nullable = false, length = 20)
    private String status;

    @Column(name = "published_at")
    private OffsetDateTime publishedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "thumbnail_media_id")
    private MediaFile thumbnailMedia;

    public void update(String title, String slug, String summary, String contentMarkdown, String status) {
        this.title = title;
        this.slug = slug;
        this.summary = summary;
        this.contentMarkdown = contentMarkdown;
        this.status = status;
        if ("PUBLISHED".equals(status) && this.publishedAt == null) {
            this.publishedAt = OffsetDateTime.now();
        }
    }
}
