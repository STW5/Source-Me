package com.stw.sourceme.profile.entity;

import com.stw.sourceme.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "site_profile")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SiteProfile extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "display_name", nullable = false, length = 100)
    private String displayName;

    @Column(nullable = false, length = 200)
    private String headline;

    @Column(name = "bio_markdown", nullable = false, columnDefinition = "TEXT")
    private String bioMarkdown;

    @Column(length = 120)
    private String email;

    @Column(name = "github_url", length = 255)
    private String githubUrl;

    @Column(name = "linkedin_url", length = 255)
    private String linkedinUrl;

    @Column(name = "resume_url", length = 255)
    private String resumeUrl;

    public void update(
            String displayName,
            String headline,
            String bioMarkdown,
            String email,
            String githubUrl,
            String linkedinUrl,
            String resumeUrl
    ) {
        this.displayName = displayName;
        this.headline = headline;
        this.bioMarkdown = bioMarkdown;
        this.email = email;
        this.githubUrl = githubUrl;
        this.linkedinUrl = linkedinUrl;
        this.resumeUrl = resumeUrl;
    }
}
