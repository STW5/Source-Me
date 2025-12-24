package com.stw.sourceme.profile.controller;

import com.stw.sourceme.profile.service.SiteProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class SiteProfileController {

    private final SiteProfileService siteProfileService;
}
