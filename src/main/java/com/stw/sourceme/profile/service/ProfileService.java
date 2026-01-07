package com.stw.sourceme.profile.service;

import com.stw.sourceme.common.exception.ErrorCode;
import com.stw.sourceme.common.exception.ResourceNotFoundException;
import com.stw.sourceme.profile.controller.dto.ProfileCreateRequest;
import com.stw.sourceme.profile.controller.dto.ProfileResponse;
import com.stw.sourceme.profile.entity.SiteProfile;
import com.stw.sourceme.profile.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProfileService {

    private final ProfileRepository profileRepository;

    public ProfileResponse getProfile() {
        SiteProfile profile = profileRepository.findAll().stream()
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.PROFILE_NOT_FOUND));

        return ProfileResponse.from(profile);
    }

    @Transactional
    public ProfileResponse setProfile(ProfileCreateRequest request) {
        SiteProfile profile = profileRepository.findAll().stream()
                .findFirst()
                .orElse(null);

        if (profile == null) {
            profile = profileRepository.save(request.toEntity());
        } else {
            profile.update(
                    request.getDisplayName(),
                    request.getHeadline(),
                    request.getBioMarkdown(),
                    request.getEmail(),
                    request.getGithubUrl(),
                    request.getLinkedinUrl(),
                    request.getResumeUrl(),
                    request.getProfilePicture()
            );
        }

        return ProfileResponse.from(profile);
    }
}
