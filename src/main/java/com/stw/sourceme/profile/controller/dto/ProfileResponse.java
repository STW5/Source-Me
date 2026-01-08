package com.stw.sourceme.profile.controller.dto;

import lombok.Builder;
import lombok.Getter;

import com.stw.sourceme.media.controller.dto.MediaFileResponse;
import com.stw.sourceme.profile.entity.SiteProfile;

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
    private MediaFileResponse profileMedia;

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
                .profileMedia(MediaFileResponse.from(siteProfile.getProfileMedia()))
                .build();
    }
}
