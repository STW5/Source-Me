package com.stw.sourceme.project.controller;

import com.stw.sourceme.common.ApiResponse;
import com.stw.sourceme.common.SecurityUtil;
import com.stw.sourceme.project.controller.dto.ProjectListResponse;
import com.stw.sourceme.project.service.ProjectBookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/projects")
@RequiredArgsConstructor
public class ProjectBookmarkController {

    private final ProjectBookmarkService projectBookmarkService;

    /**
     * 북마크 토글 (추가/제거)
     * @param projectId 프로젝트 ID
     * @return bookmarked: true면 북마크 추가, false면 제거
     */
    @PostMapping("/{projectId}/bookmark")
    public ResponseEntity<ApiResponse<Map<String, Boolean>>> toggleBookmark(@PathVariable Long projectId) {
        Long userId = SecurityUtil.getCurrentUserId();
        boolean bookmarked = projectBookmarkService.toggleBookmark(userId, projectId);
        return ResponseEntity.ok(ApiResponse.success(Map.of("bookmarked", bookmarked)));
    }

    /**
     * 북마크 여부 확인
     */
    @GetMapping("/{projectId}/bookmark/status")
    public ResponseEntity<ApiResponse<Map<String, Boolean>>> checkBookmarkStatus(@PathVariable Long projectId) {
        Long userId = SecurityUtil.getCurrentUserId();
        boolean bookmarked = projectBookmarkService.isBookmarked(userId, projectId);
        return ResponseEntity.ok(ApiResponse.success(Map.of("bookmarked", bookmarked)));
    }

    /**
     * 사용자의 북마크 목록 조회
     */
    @GetMapping("/bookmarks")
    public ResponseEntity<ApiResponse<Page<ProjectListResponse>>> getBookmarkedProjects(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Long userId = SecurityUtil.getCurrentUserId();
        Pageable pageable = PageRequest.of(page, size);
        Page<ProjectListResponse> bookmarkedProjects = projectBookmarkService.getBookmarkedProjects(userId, pageable);
        return ResponseEntity.ok(ApiResponse.success(bookmarkedProjects));
    }
}
