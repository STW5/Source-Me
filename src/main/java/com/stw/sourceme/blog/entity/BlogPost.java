package com.stw.sourceme.blog.entity;

import com.stw.sourceme.common.BaseEntity;
import com.stw.sourceme.media.entity.MediaFile;
import com.stw.sourceme.tag.entity.Tag;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "blog_post")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class BlogPost extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 200)
    private String title;

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

    @ManyToMany
    @JoinTable(
            name = "blog_post_tag",
            joinColumns = @JoinColumn(name = "blog_post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    @Builder.Default
    private List<Tag> tags = new ArrayList<>();

    public void update(String title, String summary, String contentMarkdown, String status) {
        this.title = title;
        this.summary = summary;
        this.contentMarkdown = contentMarkdown;
        this.status = status;
        if ("PUBLISHED".equals(status) && this.publishedAt == null) {
            this.publishedAt = OffsetDateTime.now();
        }
    }

    public void updateTags(List<Tag> tags) {
        this.tags.clear();
        this.tags.addAll(tags);
    }
}
