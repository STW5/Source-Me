package com.stw.sourceme.profile.service;

import com.stw.sourceme.profile.repository.SiteProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SiteProfileService {

    private final SiteProfileRepository siteProfileRepository;
}
