package com.stw.sourceme.project.entity;

import com.stw.sourceme.tag.entity.Tag;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "project_tag")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectTag {

    @EmbeddedId
    private ProjectTagId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("projectId")
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("tagId")
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @Embeddable
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ProjectTagId implements Serializable {
        @Column(name = "project_id")
        private Long projectId;

        @Column(name = "tag_id")
        private Long tagId;
    }
}
