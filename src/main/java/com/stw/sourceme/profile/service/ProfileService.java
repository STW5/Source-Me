package com.stw.sourceme.profile.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.stw.sourceme.common.exception.BusinessException;
import com.stw.sourceme.common.exception.ErrorCode;
import com.stw.sourceme.common.exception.ResourceNotFoundException;
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

        if (profile == null) {
            validateCreateRequest(request);
            MediaFile profileMedia = resolveProfileMedia(request.getProfilePictureId());
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
            MediaFile profileMedia = request.getProfilePictureId() == null
                    ? profile.getProfileMedia()
                    : resolveProfileMedia(request.getProfilePictureId());
            profile.update(
                    request.getDisplayName() == null ? profile.getDisplayName() : request.getDisplayName(),
                    request.getHeadline() == null ? profile.getHeadline() : request.getHeadline(),
                    request.getBioMarkdown() == null ? profile.getBioMarkdown() : request.getBioMarkdown(),
                    request.getEmail() == null ? profile.getEmail() : request.getEmail(),
                    request.getGithubUrl() == null ? profile.getGithubUrl() : request.getGithubUrl(),
                    request.getLinkedinUrl() == null ? profile.getLinkedinUrl() : request.getLinkedinUrl(),
                    request.getResumeUrl() == null ? profile.getResumeUrl() : request.getResumeUrl(),
                    request.getCareerGoal() == null ? profile.getCareerGoal() : request.getCareerGoal(),
                    request.getExperienceHighlights() == null ? profile.getExperienceHighlights() : request.getExperienceHighlights(),
                    request.getSkillsProficient() == null ? profile.getSkillsProficient() : request.getSkillsProficient(),
                    request.getSkillsEducation() == null ? profile.getSkillsEducation() : request.getSkillsEducation(),
                    request.getSkillsCanUse() == null ? profile.getSkillsCanUse() : request.getSkillsCanUse(),
                    request.getBackendExperience() == null ? profile.getBackendExperience() : request.getBackendExperience(),
                    profileMedia,
                    request.getInternships() == null ? profile.getInternships() : request.getInternships(),
                    request.getEducation() == null ? profile.getEducation() : request.getEducation(),
                    request.getWorkHistory() == null ? profile.getWorkHistory() : request.getWorkHistory(),
                    request.getPublicationsPatents() == null ? profile.getPublicationsPatents() : request.getPublicationsPatents(),
                    request.getCertificates() == null ? profile.getCertificates() : request.getCertificates()
            );
        }

        return ProfileResponse.from(profile);
    }

    private void validateCreateRequest(ProfileCreateRequest request) {
        if (!StringUtils.hasText(request.getDisplayName())
                || !StringUtils.hasText(request.getHeadline())
                || !StringUtils.hasText(request.getBioMarkdown())) {
            throw new BusinessException(ErrorCode.INVALID_INPUT_VALUE);
        }
    }

    private MediaFile resolveProfileMedia(Long profilePictureId) {
        if (profilePictureId == null) {
            return null;
        }
        return mediaFileRepository.findById(profilePictureId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEDIA_NOT_FOUND));
    }
}
