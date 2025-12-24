package com.stw.sourceme.project.entity;

import com.stw.sourceme.media.entity.MediaFile;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "project_media")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectMedia {

    @EmbeddedId
    private ProjectMediaId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("projectId")
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("mediaId")
    @JoinColumn(name = "media_id")
    private MediaFile media;

    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder = 0;

    @Embeddable
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ProjectMediaId implements Serializable {
        @Column(name = "project_id")
        private Long projectId;

        @Column(name = "media_id")
        private Long mediaId;
    }
}
