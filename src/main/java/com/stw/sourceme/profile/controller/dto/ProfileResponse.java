package com.stw.sourceme.profile.controller.dto;

import lombok.Builder;
import lombok.Getter;

import com.stw.sourceme.media.presentation.dto.MediaFileResponse;
import com.stw.sourceme.profile.domain.SiteProfile;
import com.stw.sourceme.profile.entity.vo.*;

import java.util.List;

@Getter
@Builder
public class ProfileResponse {

    private Long id;
    private String displayName;
    private String headline;
    private String bioMarkdown;
    private String email;
    private String githubUrl;
    private String linkedinUrl;
    private String resumeUrl;
    private String careerGoal;
    private String experienceHighlights;
    private String skillsProficient;
    private String skillsEducation;
    private String skillsCanUse;
    private String backendExperience;
    private MediaFileResponse profileMedia;
    private List<InternshipEntry> internships;
    private List<EducationEntry> education;
    private List<WorkHistoryEntry> workHistory;
    private List<PublicationPatentEntry> publicationsPatents;
    private List<CertificateEntry> certificates;

    public static ProfileResponse from(SiteProfile siteProfile) {
        return ProfileResponse.builder()
                .id(siteProfile.getId())
                .displayName(siteProfile.getDisplayName())
                .headline(siteProfile.getHeadline())
                .bioMarkdown(siteProfile.getBioMarkdown())
                .email(siteProfile.getEmail())
                .githubUrl(siteProfile.getGithubUrl())
                .linkedinUrl(siteProfile.getLinkedinUrl())
                .resumeUrl(siteProfile.getResumeUrl())
                .careerGoal(siteProfile.getCareerGoal())
                .experienceHighlights(siteProfile.getExperienceHighlights())
                .skillsProficient(siteProfile.getSkillsProficient())
                .skillsEducation(siteProfile.getSkillsEducation())
                .skillsCanUse(siteProfile.getSkillsCanUse())
                .backendExperience(siteProfile.getBackendExperience())
                .profileMedia(MediaFileResponse.from(siteProfile.getProfileMedia()))
                .internships(siteProfile.getInternships())
                .education(siteProfile.getEducation())
                .workHistory(siteProfile.getWorkHistory())
                .publicationsPatents(siteProfile.getPublicationsPatents())
                .certificates(siteProfile.getCertificates())
                .build();
    }
}
