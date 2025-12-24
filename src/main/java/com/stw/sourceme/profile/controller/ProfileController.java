package com.stw.sourceme.profile.controller;

import com.stw.sourceme.common.ApiResponse;
import com.stw.sourceme.profile.controller.dto.ProfileCreateRequest;
import com.stw.sourceme.profile.controller.dto.ProfileResponse;
import com.stw.sourceme.profile.service.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping
    public ResponseEntity<ApiResponse<ProfileResponse>> getProfile() {
        ProfileResponse data = profileService.getProfile();
        return ResponseEntity.ok(ApiResponse.success(data));
    }

    @PutMapping
    public ResponseEntity<ApiResponse<ProfileResponse>> setProfile(
            @Valid @RequestBody ProfileCreateRequest request
    ) {
        ProfileResponse data = profileService.setProfile(request);
        return ResponseEntity.ok(ApiResponse.success(data));
    }
}
