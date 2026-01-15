package com.stw.sourceme.blog.domain;

import com.stw.sourceme.tag.domain.Tag;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "blog_post_tag")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BlogPostTag {

    @EmbeddedId
    private BlogPostTagId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("blogPostId")
    @JoinColumn(name = "blog_post_id")
    private BlogPost blogPost;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("tagId")
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @Embeddable
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class BlogPostTagId implements Serializable {
        @Column(name = "blog_post_id")
        private UUID blogPostId;

        @Column(name = "tag_id")
        private Long tagId;
    }
}
