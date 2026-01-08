package com.stw.sourceme.media.controller.dto;

import java.time.OffsetDateTime;

import lombok.Builder;
import lombok.Getter;

import com.stw.sourceme.media.entity.MediaFile;
@Getter
@Builder
public class MediaFileResponse {
    private Long id;
    private String storageType;
    private String fileKey;
    private String publicUrl;
    private String originalName;
    private String contentType;
    private Long sizeBytes;
    private OffsetDateTime createdAt;

    public static MediaFileResponse from(MediaFile mediaFile) {
        if (mediaFile == null) {
            return null;
        }

        return MediaFileResponse.builder()
                .id(mediaFile.getId())
                .storageType(mediaFile.getStorageType())
                .fileKey(mediaFile.getFileKey())
                .publicUrl(mediaFile.getPublicUrl())
                .originalName(mediaFile.getOriginalName())
                .contentType(mediaFile.getContentType())
                .sizeBytes(mediaFile.getSizeBytes())
                .createdAt(mediaFile.getCreatedAt())
                .build();
    }
}
