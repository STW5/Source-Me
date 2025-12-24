package com.stw.sourceme.profile.controller;

import com.stw.sourceme.common.ApiResponse;
import com.stw.sourceme.profile.controller.dto.SiteProfileCreateRequest;
import com.stw.sourceme.profile.controller.dto.SiteProfileResponse;
import com.stw.sourceme.profile.service.SiteProfileService;
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
public class SiteProfileController {

    private final SiteProfileService siteProfileService;

    @GetMapping
    public ResponseEntity<ApiResponse<SiteProfileResponse>> getProfile() {
        SiteProfileResponse data = siteProfileService.getProfile();
        return ResponseEntity.ok(ApiResponse.success(data));
    }

    @PutMapping
    public ResponseEntity<ApiResponse<SiteProfileResponse>> setProfile(
            @Valid @RequestBody SiteProfileCreateRequest request
    ) {
        SiteProfileResponse data = siteProfileService.setProfile(request);
        return ResponseEntity.ok(ApiResponse.success(data));
    }
}
