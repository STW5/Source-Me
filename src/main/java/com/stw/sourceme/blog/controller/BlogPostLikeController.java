package com.stw.sourceme.blog.controller;

import com.stw.sourceme.blog.service.BlogPostLikeService;
import com.stw.sourceme.common.ApiResponse;
import com.stw.sourceme.common.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/blog/posts")
@RequiredArgsConstructor
public class BlogPostLikeController {

    private final BlogPostLikeService blogPostLikeService;

    /**
     * 좋아요 토글 (추가/제거)
     * @param postId 블로그 게시글 ID
     * @return liked: true면 좋아요 추가, false면 제거
     */
    @PostMapping("/{postId}/like")
    public ResponseEntity<ApiResponse<Map<String, Boolean>>> toggleLike(@PathVariable UUID postId) {
        Long userId = SecurityUtil.getCurrentUserId();
        boolean liked = blogPostLikeService.toggleLike(userId, postId);
        return ResponseEntity.ok(ApiResponse.success(Map.of("liked", liked)));
    }

    /**
     * 좋아요 여부 확인
     */
    @GetMapping("/{postId}/like/status")
    public ResponseEntity<ApiResponse<Map<String, Boolean>>> checkLikeStatus(@PathVariable UUID postId) {
        Long userId = SecurityUtil.getCurrentUserId();
        boolean liked = blogPostLikeService.isLiked(userId, postId);
        return ResponseEntity.ok(ApiResponse.success(Map.of("liked", liked)));
    }

    /**
     * 좋아요 수 조회
     */
    @GetMapping("/{postId}/like/count")
    public ResponseEntity<ApiResponse<Map<String, Long>>> getLikeCount(
            @PathVariable UUID postId) {
        long count = blogPostLikeService.getLikeCount(postId);
        return ResponseEntity.ok(ApiResponse.success(Map.of("count", count)));
    }
}
