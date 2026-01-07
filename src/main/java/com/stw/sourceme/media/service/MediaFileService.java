package com.stw.sourceme.media.service;

import com.stw.sourceme.common.exception.BusinessException;
import com.stw.sourceme.common.exception.ErrorCode;
import com.stw.sourceme.media.entity.MediaFile;
import com.stw.sourceme.media.repository.MediaFileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MediaFileService {

    private final MediaFileRepository mediaFileRepository;

    @Value("${app.upload.dir:uploads}")
    private String uploadDir;

    @Value("${app.upload.max-file-size:10485760}")
    private long maxFileSize;

    private static final List<String> ALLOWED_IMAGE_TYPES = Arrays.asList(
            "image/jpeg", "image/jpg", "image/png", "image/gif", "image/webp"
    );

    private static final List<String> ALLOWED_DOCUMENT_TYPES = Arrays.asList(
            "application/pdf", "text/plain", "application/msword",
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
    );

    @Transactional
    public MediaFile uploadFile(MultipartFile file) {
        validateFile(file);

        String fileKey = generateFileKey(file);
        Path uploadPath = getUploadPath();
        
        try {
            Files.createDirectories(uploadPath);
            Path filePath = uploadPath.resolve(fileKey);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            MediaFile mediaFile = MediaFile.builder()
                    .storageType("LOCAL")
                    .fileKey(fileKey)
                    .publicUrl("/api/media/files/" + fileKey)
                    .originalName(StringUtils.cleanPath(file.getOriginalFilename()))
                    .contentType(file.getContentType())
                    .sizeBytes(file.getSize())
                    .build();

            return mediaFileRepository.save(mediaFile);
        } catch (IOException e) {
            log.error("Failed to upload file: {}", file.getOriginalFilename(), e);
            throw new BusinessException(ErrorCode.MEDIA_UPLOAD_FAILED);
        }
    }

    @Transactional
    public void deleteFile(Long id) {
        MediaFile mediaFile = mediaFileRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEDIA_NOT_FOUND));

        try {
            Path filePath = getUploadPath().resolve(mediaFile.getFileKey());
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            log.warn("Failed to delete file: {}", mediaFile.getFileKey(), e);
        }

        mediaFileRepository.delete(mediaFile);
    }

    public MediaFile getFile(Long id) {
        return mediaFileRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEDIA_NOT_FOUND));
    }

    public MediaFile getFileByFileKey(String fileKey) {
        return mediaFileRepository.findByFileKey(fileKey)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEDIA_NOT_FOUND));
    }

    public Path getFilePath(String fileKey) {
        return getUploadPath().resolve(fileKey);
    }

    private void validateFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new BusinessException(ErrorCode.MEDIA_FILE_EMPTY);
        }

        if (file.getSize() > maxFileSize) {
            throw new BusinessException(ErrorCode.MEDIA_FILE_TOO_LARGE, 
                "파일 크기가 최대 크기를 초과했습니다. (" + (maxFileSize / 1024 / 1024) + "MB)");
        }

        String contentType = file.getContentType();
        if (contentType == null || !isAllowedContentType(contentType)) {
            throw new BusinessException(ErrorCode.MEDIA_UNSUPPORTED_TYPE);
        }
    }

    private boolean isAllowedContentType(String contentType) {
        return ALLOWED_IMAGE_TYPES.contains(contentType) || ALLOWED_DOCUMENT_TYPES.contains(contentType);
    }

    private String generateFileKey(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        return UUID.randomUUID().toString() + extension;
    }

    private Path getUploadPath() {
        return Paths.get(uploadDir).toAbsolutePath().normalize();
    }
}
