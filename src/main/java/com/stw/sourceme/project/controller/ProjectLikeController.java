package com.stw.sourceme.project.controller;

import com.stw.sourceme.common.ApiResponse;
import com.stw.sourceme.project.service.ProjectLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/projects")
@RequiredArgsConstructor
public class ProjectLikeController {

    private final ProjectLikeService projectLikeService;

    /**
     * 좋아요 토글 (추가/제거)
     * @param projectId 프로젝트 ID
     * @param userId 사용자 ID (실제로는 인증에서 가져와야 함)
     * @return liked: true면 좋아요 추가, false면 제거
     */
    @PostMapping("/{projectId}/like")
    public ResponseEntity<ApiResponse<Map<String, Boolean>>> toggleLike(
            @PathVariable Long projectId,
            @RequestParam Long userId) { // TODO: 실제로는 Security Context에서 가져와야 함
        boolean liked = projectLikeService.toggleLike(userId, projectId);
        return ResponseEntity.ok(ApiResponse.success(Map.of("liked", liked)));
    }

    /**
     * 좋아요 여부 확인
     */
    @GetMapping("/{projectId}/like/status")
    public ResponseEntity<ApiResponse<Map<String, Boolean>>> checkLikeStatus(
            @PathVariable Long projectId,
            @RequestParam Long userId) {
        boolean liked = projectLikeService.isLiked(userId, projectId);
        return ResponseEntity.ok(ApiResponse.success(Map.of("liked", liked)));
    }

    /**
     * 좋아요 수 조회
     */
    @GetMapping("/{projectId}/like/count")
    public ResponseEntity<ApiResponse<Map<String, Long>>> getLikeCount(
            @PathVariable Long projectId) {
        long count = projectLikeService.getLikeCount(projectId);
        return ResponseEntity.ok(ApiResponse.success(Map.of("count", count)));
    }
}
