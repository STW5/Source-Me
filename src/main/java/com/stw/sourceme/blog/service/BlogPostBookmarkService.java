package com.stw.sourceme.blog.service;

import com.stw.sourceme.blog.controller.dto.BlogPostListResponse;
import com.stw.sourceme.blog.entity.BlogPost;
import com.stw.sourceme.blog.entity.BlogPostBookmark;
import com.stw.sourceme.blog.repository.BlogPostBookmarkRepository;
import com.stw.sourceme.blog.repository.BlogPostRepository;
import com.stw.sourceme.common.exception.ErrorCode;
import com.stw.sourceme.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BlogPostBookmarkService {

    private final BlogPostBookmarkRepository blogPostBookmarkRepository;
    private final BlogPostRepository blogPostRepository;

    /**
     * 북마크 추가/제거 토글
     * @param userId 사용자 ID
     * @param blogPostId 블로그 게시글 ID
     * @return true: 북마크 추가됨, false: 북마크 제거됨
     */
    @Transactional
    public boolean toggleBookmark(Long userId, UUID blogPostId) {
        // 게시글 존재 확인
        blogPostRepository.findById(blogPostId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.BLOG_POST_NOT_FOUND));

        // 이미 북마크했는지 확인
        if (blogPostBookmarkRepository.existsByUserIdAndBlogPostId(userId, blogPostId)) {
            // 북마크 제거
            blogPostBookmarkRepository.deleteByUserIdAndBlogPostId(userId, blogPostId);
            return false;
        } else {
            // 북마크 추가
            BlogPostBookmark bookmark = BlogPostBookmark.builder()
                    .userId(userId)
                    .blogPostId(blogPostId)
                    .build();
            blogPostBookmarkRepository.save(bookmark);
            return true;
        }
    }

    /**
     * 특정 사용자가 게시글을 북마크했는지 확인
     */
    public boolean isBookmarked(Long userId, UUID blogPostId) {
        return blogPostBookmarkRepository.existsByUserIdAndBlogPostId(userId, blogPostId);
    }

    /**
     * 사용자의 북마크 목록 조회
     */
    public Page<BlogPostListResponse> getBookmarkedPosts(Long userId, Pageable pageable) {
        Page<BlogPost> bookmarkedPosts = blogPostBookmarkRepository.findBookmarkedPostsByUserId(userId, pageable);
        return bookmarkedPosts.map(BlogPostListResponse::from);
    }
}
