package com.stw.sourceme.media.controller;

import com.stw.sourceme.media.service.MediaFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/media")
@RequiredArgsConstructor
public class MediaFileController {

    private final MediaFileService mediaFileService;
}
