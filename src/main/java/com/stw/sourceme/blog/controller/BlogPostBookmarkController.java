package com.stw.sourceme.blog.controller;

import com.stw.sourceme.blog.controller.dto.BlogPostListResponse;
import com.stw.sourceme.blog.service.BlogPostBookmarkService;
import com.stw.sourceme.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/blog")
@RequiredArgsConstructor
public class BlogPostBookmarkController {

    private final BlogPostBookmarkService blogPostBookmarkService;

    /**
     * 북마크 토글 (추가/제거)
     * @param postId 블로그 게시글 ID
     * @param userId 사용자 ID (실제로는 인증에서 가져와야 함)
     * @return bookmarked: true면 북마크 추가, false면 제거
     */
    @PostMapping("/posts/{postId}/bookmark")
    public ResponseEntity<ApiResponse<Map<String, Boolean>>> toggleBookmark(
            @PathVariable UUID postId,
            @RequestParam Long userId) { // TODO: 실제로는 Security Context에서 가져와야 함
        boolean bookmarked = blogPostBookmarkService.toggleBookmark(userId, postId);
        return ResponseEntity.ok(ApiResponse.success(Map.of("bookmarked", bookmarked)));
    }

    /**
     * 북마크 여부 확인
     */
    @GetMapping("/posts/{postId}/bookmark/status")
    public ResponseEntity<ApiResponse<Map<String, Boolean>>> checkBookmarkStatus(
            @PathVariable UUID postId,
            @RequestParam Long userId) {
        boolean bookmarked = blogPostBookmarkService.isBookmarked(userId, postId);
        return ResponseEntity.ok(ApiResponse.success(Map.of("bookmarked", bookmarked)));
    }

    /**
     * 사용자의 북마크 목록 조회
     */
    @GetMapping("/bookmarks")
    public ResponseEntity<ApiResponse<Page<BlogPostListResponse>>> getBookmarkedPosts(
            @RequestParam Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<BlogPostListResponse> bookmarkedPosts = blogPostBookmarkService.getBookmarkedPosts(userId, pageable);
        return ResponseEntity.ok(ApiResponse.success(bookmarkedPosts));
    }
}
