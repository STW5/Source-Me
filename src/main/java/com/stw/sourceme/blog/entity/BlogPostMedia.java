package com.stw.sourceme.blog.entity;

import com.stw.sourceme.media.entity.MediaFile;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "blog_post_media")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BlogPostMedia {

    @EmbeddedId
    private BlogPostMediaId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("blogPostId")
    @JoinColumn(name = "blog_post_id")
    private BlogPost blogPost;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("mediaId")
    @JoinColumn(name = "media_id")
    private MediaFile media;

    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder = 0;

    @Embeddable
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class BlogPostMediaId implements Serializable {
        @Column(name = "blog_post_id")
        private UUID blogPostId;

        @Column(name = "media_id")
        private Long mediaId;
    }
}
