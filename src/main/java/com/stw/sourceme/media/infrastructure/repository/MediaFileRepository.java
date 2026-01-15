package com.stw.sourceme.media.infrastructure.repository;

import com.stw.sourceme.media.domain.MediaFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MediaFileRepository extends JpaRepository<MediaFile, Long> {
    Optional<MediaFile> findByFileKey(String fileKey);
}
