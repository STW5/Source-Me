package com.stw.sourceme.profile.controller.dto;

import com.stw.sourceme.profile.entity.SiteProfile;
import lombok.Builder;
import lombok.Getter;

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
                .build();
    }
}
