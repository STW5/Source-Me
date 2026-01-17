package com.stw.sourceme.profile.service;

import com.stw.sourceme.common.exception.ErrorCode;
import com.stw.sourceme.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stw.sourceme.common.exception.BusinessException;
import com.stw.sourceme.common.exception.ErrorCode;
import com.stw.sourceme.media.entity.MediaFile;
import com.stw.sourceme.media.repository.MediaFileRepository;
import com.stw.sourceme.profile.controller.dto.ProfileCreateRequest;
import com.stw.sourceme.profile.controller.dto.ProfileResponse;
import com.stw.sourceme.profile.entity.SiteProfile;
import com.stw.sourceme.profile.repository.ProfileRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final MediaFileRepository mediaFileRepository;

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

        MediaFile profileMedia = resolveProfileMedia(request.getProfilePictureId());
        if (profile == null) {
            profile = request.toEntity();
            profile.update(
                    request.getDisplayName(),
                    request.getHeadline(),
                    request.getBioMarkdown(),
                    request.getEmail(),
                    request.getGithubUrl(),
                    request.getLinkedinUrl(),
                    request.getResumeUrl(),
                    request.getCareerGoal(),
                    request.getExperienceHighlights(),
                    request.getSkillsProficient(),
                    request.getSkillsEducation(),
                    request.getSkillsCanUse(),
                    request.getBackendExperience(),
                    profileMedia,
                    request.getInternships(),
                    request.getEducation(),
                    request.getWorkHistory(),
                    request.getPublicationsPatents(),
                    request.getCertificates()
            );
            profile = profileRepository.save(profile);
        } else {
            profile.update(
                    request.getDisplayName(),
                    request.getHeadline(),
                    request.getBioMarkdown(),
                    request.getEmail(),
                    request.getGithubUrl(),
                    request.getLinkedinUrl(),
                    request.getResumeUrl(),
                    request.getCareerGoal(),
                    request.getExperienceHighlights(),
                    request.getSkillsProficient(),
                    request.getSkillsEducation(),
                    request.getSkillsCanUse(),
                    request.getBackendExperience(),
                    profileMedia,
                    request.getInternships(),
                    request.getEducation(),
                    request.getWorkHistory(),
                    request.getPublicationsPatents(),
                    request.getCertificates()
            );
        }

        return ProfileResponse.from(profile);
    }

    private MediaFile resolveProfileMedia(Long profilePictureId) {
        if (profilePictureId == null) {
            return null;
        }
        return mediaFileRepository.findById(profilePictureId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEDIA_NOT_FOUND));
    }
}
