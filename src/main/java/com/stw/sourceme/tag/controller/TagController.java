package com.stw.sourceme.tag.controller;

import com.stw.sourceme.common.response.ApiResponse;
import com.stw.sourceme.tag.controller.dto.TagCreateRequest;
import com.stw.sourceme.tag.controller.dto.TagResponse;
import com.stw.sourceme.tag.controller.dto.TagUpdateRequest;
import com.stw.sourceme.tag.service.TagService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    // 전체 태그 목록 조회 (public)
    @GetMapping
    public ResponseEntity<ApiResponse<List<TagResponse>>> getAllTags() {
        List<TagResponse> tags = tagService.getAllTags();
        return ResponseEntity.ok(ApiResponse.success(tags));
    }

    // 태그 ID로 조회 (public)
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TagResponse>> getTagById(@PathVariable Long id) {
        TagResponse tag = tagService.getTagById(id);
        return ResponseEntity.ok(ApiResponse.success(tag));
    }

    // 태그 생성 (관리자용)
    @PostMapping
    public ResponseEntity<ApiResponse<TagResponse>> createTag(@Valid @RequestBody TagCreateRequest request) {
        TagResponse tag = tagService.createTag(request);
        return ResponseEntity.ok(ApiResponse.success(tag));
    }

    // 태그 수정 (관리자용)
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TagResponse>> updateTag(
            @PathVariable Long id,
            @Valid @RequestBody TagUpdateRequest request) {
        TagResponse tag = tagService.updateTag(id, request);
        return ResponseEntity.ok(ApiResponse.success(tag));
    }

    // 태그 삭제 (관리자용)
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTag(@PathVariable Long id) {
        tagService.deleteTag(id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
