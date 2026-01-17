package com.stw.sourceme.profile.entity;

import com.stw.sourceme.common.BaseEntity;
import com.stw.sourceme.media.domain.MediaFile;
import com.stw.sourceme.profile.entity.vo.*;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;

@Entity
@Table(name = "profile")
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

    @Column(name = "career_goal", columnDefinition = "TEXT")
    private String careerGoal;

    @Column(name = "experience_highlights", columnDefinition = "TEXT")
    private String experienceHighlights;

    @Column(name = "skills_proficient", columnDefinition = "TEXT")
    private String skillsProficient;

    @Column(name = "skills_education", columnDefinition = "TEXT")
    private String skillsEducation;

    @Column(name = "skills_can_use", columnDefinition = "TEXT")
    private String skillsCanUse;

    @Column(name = "backend_experience", columnDefinition = "TEXT")
    private String backendExperience;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_media_id", foreignKey = @ForeignKey(name = "fk_profile_media"))
    private MediaFile profileMedia;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "internships", columnDefinition = "jsonb")
    private List<InternshipEntry> internships;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "education", columnDefinition = "jsonb")
    private List<EducationEntry> education;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "work_history", columnDefinition = "jsonb")
    private List<WorkHistoryEntry> workHistory;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "publications_patents", columnDefinition = "jsonb")
    private List<PublicationPatentEntry> publicationsPatents;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "certificates", columnDefinition = "jsonb")
    private List<CertificateEntry> certificates;

    public void update(
            String displayName,
            String headline,
            String bioMarkdown,
            String email,
            String githubUrl,
            String linkedinUrl,
            String resumeUrl,
            String careerGoal,
            String experienceHighlights,
            String skillsProficient,
            String skillsEducation,
            String skillsCanUse,
            String backendExperience,
            MediaFile profileMedia,
            List<InternshipEntry> internships,
            List<EducationEntry> education,
            List<WorkHistoryEntry> workHistory,
            List<PublicationPatentEntry> publicationsPatents,
            List<CertificateEntry> certificates
    ) {
        this.displayName = displayName;
        this.headline = headline;
        this.bioMarkdown = bioMarkdown;
        this.email = email;
        this.githubUrl = githubUrl;
        this.linkedinUrl = linkedinUrl;
        this.resumeUrl = resumeUrl;
        this.careerGoal = careerGoal;
        this.experienceHighlights = experienceHighlights;
        this.skillsProficient = skillsProficient;
        this.skillsEducation = skillsEducation;
        this.skillsCanUse = skillsCanUse;
        this.backendExperience = backendExperience;
        this.profileMedia = profileMedia;
        this.internships = internships;
        this.education = education;
        this.workHistory = workHistory;
        this.publicationsPatents = publicationsPatents;
        this.certificates = certificates;
    }
}
