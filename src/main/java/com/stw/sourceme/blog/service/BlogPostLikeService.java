package com.stw.sourceme.blog.service;

import com.stw.sourceme.blog.entity.BlogPost;
import com.stw.sourceme.blog.entity.BlogPostLike;
import com.stw.sourceme.blog.repository.BlogPostLikeRepository;
import com.stw.sourceme.blog.repository.BlogPostRepository;
import com.stw.sourceme.common.exception.ErrorCode;
import com.stw.sourceme.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BlogPostLikeService {

    private final BlogPostLikeRepository blogPostLikeRepository;
    private final BlogPostRepository blogPostRepository;

    /**
     * 좋아요 추가/제거 토글
     * @param userId 사용자 ID
     * @param blogPostId 블로그 게시글 ID
     * @return true: 좋아요 추가됨, false: 좋아요 제거됨
     */
    @Transactional
    public boolean toggleLike(Long userId, UUID blogPostId) {
        // 게시글 존재 확인
        BlogPost blogPost = blogPostRepository.findById(blogPostId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.BLOG_POST_NOT_FOUND));

        // 이미 좋아요를 눌렀는지 확인
        if (blogPostLikeRepository.existsByUserIdAndBlogPostId(userId, blogPostId)) {
            // 좋아요 제거
            blogPostLikeRepository.deleteByUserIdAndBlogPostId(userId, blogPostId);
            blogPost.decrementLikeCount();
            return false;
        } else {
            // 좋아요 추가
            BlogPostLike like = BlogPostLike.builder()
                    .userId(userId)
                    .blogPostId(blogPostId)
                    .build();
            blogPostLikeRepository.save(like);
            blogPost.incrementLikeCount();
            return true;
        }
    }

    /**
     * 특정 사용자가 게시글에 좋아요를 눌렀는지 확인
     */
    public boolean isLiked(Long userId, UUID blogPostId) {
        return blogPostLikeRepository.existsByUserIdAndBlogPostId(userId, blogPostId);
    }

    /**
     * 게시글의 좋아요 수 조회
     */
    public long getLikeCount(UUID blogPostId) {
        return blogPostLikeRepository.countByBlogPostId(blogPostId);
    }
}
