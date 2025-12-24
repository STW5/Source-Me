package com.stw.sourceme.profile.service;

import com.stw.sourceme.common.exception.ErrorCode;
import com.stw.sourceme.common.exception.ResourceNotFoundException;
import com.stw.sourceme.profile.controller.dto.SiteProfileCreateRequest;
import com.stw.sourceme.profile.controller.dto.SiteProfileResponse;
import com.stw.sourceme.profile.entity.SiteProfile;
import com.stw.sourceme.profile.repository.SiteProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SiteProfileService {

    private final SiteProfileRepository siteProfileRepository;

    public SiteProfileResponse getProfile() {
        SiteProfile profile = siteProfileRepository.findAll().stream()
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.PROFILE_NOT_FOUND));

        return SiteProfileResponse.from(profile);
    }

    @Transactional
    public SiteProfileResponse setProfile(SiteProfileCreateRequest request) {
        SiteProfile profile = siteProfileRepository.findAll().stream()
                .findFirst()
                .orElse(null);

        if (profile == null) {
            profile = siteProfileRepository.save(request.toEntity());
        } else {
            profile.update(
                    request.getDisplayName(),
                    request.getHeadline(),
                    request.getBioMarkdown(),
                    request.getEmail(),
                    request.getGithubUrl(),
                    request.getLinkedinUrl(),
                    request.getResumeUrl()
            );
        }

        return SiteProfileResponse.from(profile);
    }
}
