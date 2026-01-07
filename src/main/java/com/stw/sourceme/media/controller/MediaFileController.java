package com.stw.sourceme.media.controller;

import com.stw.sourceme.common.ApiResponse;
import com.stw.sourceme.media.entity.MediaFile;
import com.stw.sourceme.media.service.MediaFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("/api/media")
@RequiredArgsConstructor
public class MediaFileController {

    private final MediaFileService mediaFileService;

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<MediaFile>> uploadFile(@RequestParam("file") MultipartFile file) {
        MediaFile mediaFile = mediaFileService.uploadFile(file);
        return ResponseEntity.ok(ApiResponse.success(mediaFile));
    }

    @PostMapping("/upload/multiple")
    public ResponseEntity<ApiResponse<List<MediaFile>>> uploadMultipleFiles(@RequestParam("files") List<MultipartFile> files) {
        List<MediaFile> mediaFiles = files.stream()
                .map(mediaFileService::uploadFile)
                .toList();
        return ResponseEntity.ok(ApiResponse.success(mediaFiles));
    }

    @GetMapping("/files/{fileKey}")
    public ResponseEntity<Resource> getFile(@PathVariable String fileKey) throws IOException {
        Path filePath = mediaFileService.getFilePath(fileKey);
        Resource resource = new UrlResource(filePath.toUri());

        if (!resource.exists() || !resource.isReadable()) {
            return ResponseEntity.notFound().build();
        }

        MediaFile mediaFile = mediaFileService.getFileByFileKey(fileKey);
        String contentType = mediaFile.getContentType();
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + mediaFile.getOriginalName() + "\"")
                .body(resource);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MediaFile>> getFile(@PathVariable Long id) {
        MediaFile mediaFile = mediaFileService.getFile(id);
        return ResponseEntity.ok(ApiResponse.success(mediaFile));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteFile(@PathVariable Long id) {
        mediaFileService.deleteFile(id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
