package com.stw.sourceme.profile.controller.dto;

import com.stw.sourceme.profile.domain.SiteProfile;
import com.stw.sourceme.profile.entity.vo.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ProfileCreateRequest {

    @NotBlank(message = "이름은 필수입니다.")
    @Size(max = 100, message = "이름은 100자 이하여야 합니다.")
    private String displayName;

    @NotBlank(message = "한 줄 소개는 필수입니다.")
    @Size(max = 200, message = "한 줄 소개는 200자 이하여야 합니다.")
    private String headline;

    @NotBlank(message = "소개 본문은 필수입니다.")
    private String bioMarkdown;

    @Size(max = 120, message = "이메일은 120자 이하여야 합니다.")
    private String email;

    @Size(max = 255, message = "GitHub URL은 255자 이하여야 합니다.")
    private String githubUrl;

    @Size(max = 255, message = "LinkedIn URL은 255자 이하여야 합니다.")
    private String linkedinUrl;

    @Size(max = 255, message = "이력서 URL은 255자 이하여야 합니다.")
    private String resumeUrl;

    private String careerGoal;
    private String experienceHighlights;
    private String skillsProficient;
    private String skillsEducation;
    private String skillsCanUse;
    private String backendExperience;

    private Long profilePictureId;

    private List<InternshipEntry> internships;
    private List<EducationEntry> education;
    private List<WorkHistoryEntry> workHistory;
    private List<PublicationPatentEntry> publicationsPatents;
    private List<CertificateEntry> certificates;

    public SiteProfile toEntity() {
        return SiteProfile.builder()
                .displayName(displayName)
                .headline(headline)
                .bioMarkdown(bioMarkdown)
                .email(email)
                .githubUrl(githubUrl)
                .linkedinUrl(linkedinUrl)
                .resumeUrl(resumeUrl)
                .careerGoal(careerGoal)
                .experienceHighlights(experienceHighlights)
                .skillsProficient(skillsProficient)
                .skillsEducation(skillsEducation)
                .skillsCanUse(skillsCanUse)
                .backendExperience(backendExperience)
                .profileMedia(null)
                .internships(internships)
                .education(education)
                .workHistory(workHistory)
                .publicationsPatents(publicationsPatents)
                .certificates(certificates)
                .build();
    }
}
