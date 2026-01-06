package com.stw.sourceme.project.entity;

import com.stw.sourceme.common.BaseEntity;
import com.stw.sourceme.media.entity.MediaFile;
import com.stw.sourceme.tag.entity.Tag;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "project")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Project extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, unique = true, length = 220)
    private String slug;

    @Column(nullable = false, length = 300)
    private String summary;

    @Column(name = "content_markdown", nullable = false, columnDefinition = "TEXT")
    private String contentMarkdown;

    @Column(name = "started_at")
    private LocalDate startedAt;

    @Column(name = "ended_at")
    private LocalDate endedAt;

    @Column(name = "is_published", nullable = false)
    private Boolean isPublished = true;

    @Column(name = "is_featured", nullable = false)
    private Boolean isFeatured = false;

    @Column(name = "featured_order", nullable = false)
    private Integer featuredOrder = 0;

    @Column(name = "github_url", length = 255)
    private String githubUrl;

    @Column(name = "demo_url", length = 255)
    private String demoUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "thumbnail_media_id")
    private MediaFile thumbnailMedia;

    @ManyToMany
    @JoinTable(
            name = "project_tag",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    @Builder.Default
    private List<Tag> tags = new ArrayList<>();

    public void update(String title, String slug, String summary, String contentMarkdown,
                      LocalDate startedAt, LocalDate endedAt, Boolean isPublished, Boolean isFeatured,
                      Integer featuredOrder, String githubUrl, String demoUrl) {
        this.title = title;
        this.slug = slug;
        this.summary = summary;
        this.contentMarkdown = contentMarkdown;
        this.startedAt = startedAt;
        this.endedAt = endedAt;
        this.isPublished = isPublished;
        this.isFeatured = isFeatured;
        this.featuredOrder = featuredOrder;
        this.githubUrl = githubUrl;
        this.demoUrl = demoUrl;
    }

    public void updateTags(List<Tag> tags) {
        this.tags.clear();
        this.tags.addAll(tags);
    }
}
