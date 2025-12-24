package com.stw.sourceme.profile.controller.dto;

import com.stw.sourceme.profile.entity.SiteProfile;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SiteProfileCreateRequest {

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

    public SiteProfile toEntity() {
        return SiteProfile.builder()
                .displayName(displayName)
                .headline(headline)
                .bioMarkdown(bioMarkdown)
                .email(email)
                .githubUrl(githubUrl)
                .linkedinUrl(linkedinUrl)
                .resumeUrl(resumeUrl)
                .build();
    }
}
