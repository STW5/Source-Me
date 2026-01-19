package com.stw.sourceme.project.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

import com.stw.sourceme.common.BaseEntity;
import com.stw.sourceme.media.domain.MediaFile;
import com.stw.sourceme.tag.domain.Tag;

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

    @Builder.Default
    @Column(name = "is_published", nullable = false)
    private Boolean isPublished = true;

    @Builder.Default
    @Column(name = "is_featured", nullable = false)
    private Boolean isFeatured = false;

    @Builder.Default
    @Column(name = "featured_order", nullable = false)
    private Integer featuredOrder = 0;

    @Column(name = "github_url", length = 255)
    private String githubUrl;

    @Column(name = "demo_url", length = 255)
    private String demoUrl;

    @Column(name = "team_size", length = 100)
    private String teamSize;

    @Column(name = "project_role", length = 100)
    private String role;

    @Column(name = "owned_services", length = 200)
    private String ownedServices;

    @Column(name = "introduction_markdown", columnDefinition = "TEXT")
    private String introductionMarkdown;

    @Column(name = "responsibilities_markdown", columnDefinition = "TEXT")
    private String responsibilitiesMarkdown;

    @Column(name = "troubleshooting_markdown", columnDefinition = "TEXT")
    private String troubleshootingMarkdown;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "thumbnail_media_id")
    private MediaFile thumbnailMedia;

    @Builder.Default
    @Column(name = "view_count", nullable = false)
    private Long viewCount = 0L;

    @Builder.Default
    @Column(name = "like_count", nullable = false)
    private Long likeCount = 0L;

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
                      Integer featuredOrder, String githubUrl, String demoUrl, String teamSize, String role,
                      String ownedServices, String introductionMarkdown, String responsibilitiesMarkdown,
                      String troubleshootingMarkdown, MediaFile thumbnailMedia) {
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
        this.teamSize = teamSize;
        this.role = role;
        this.ownedServices = ownedServices;
        this.introductionMarkdown = introductionMarkdown;
        this.responsibilitiesMarkdown = responsibilitiesMarkdown;
        this.troubleshootingMarkdown = troubleshootingMarkdown;
        this.thumbnailMedia = thumbnailMedia;
    }

    public void updateTags(List<Tag> tags) {
        this.tags.clear();
        this.tags.addAll(tags);
    }

    public void incrementViewCount() {
        this.viewCount++;
    }

    public void incrementLikeCount() {
        this.likeCount++;
    }

    public void decrementLikeCount() {
        if (this.likeCount > 0) {
            this.likeCount--;
        }
    }
}
