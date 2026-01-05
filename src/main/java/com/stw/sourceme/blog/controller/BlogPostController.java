package com.stw.sourceme.blog.controller;

import com.stw.sourceme.blog.controller.dto.BlogPostCreateRequest;
import com.stw.sourceme.blog.controller.dto.BlogPostListResponse;
import com.stw.sourceme.blog.controller.dto.BlogPostResponse;
import com.stw.sourceme.blog.controller.dto.BlogPostUpdateRequest;
import com.stw.sourceme.blog.service.BlogPostService;
import com.stw.sourceme.common.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blog")
@RequiredArgsConstructor
public class BlogPostController {

    private final BlogPostService blogPostService;

    // 공개된 게시글 목록 조회 (public)
    @GetMapping("/posts")
    public ResponseEntity<ApiResponse<List<BlogPostListResponse>>> getPublishedPosts() {
        List<BlogPostListResponse> posts = blogPostService.getAllPublishedPosts();
        return ResponseEntity.ok(ApiResponse.success(posts));
    }

    // 모든 게시글 목록 조회 (관리자용)
    @GetMapping("/admin/posts")
    public ResponseEntity<ApiResponse<List<BlogPostListResponse>>> getAllPosts() {
        List<BlogPostListResponse> posts = blogPostService.getAllPosts();
        return ResponseEntity.ok(ApiResponse.success(posts));
    }

    // Slug로 게시글 조회 (public)
    @GetMapping("/posts/slug/{slug}")
    public ResponseEntity<ApiResponse<BlogPostResponse>> getPostBySlug(@PathVariable String slug) {
        BlogPostResponse post = blogPostService.getPostBySlug(slug);
        return ResponseEntity.ok(ApiResponse.success(post));
    }

    // ID로 게시글 조회
    @GetMapping("/posts/{id}")
    public ResponseEntity<ApiResponse<BlogPostResponse>> getPostById(@PathVariable Long id) {
        BlogPostResponse post = blogPostService.getPostById(id);
        return ResponseEntity.ok(ApiResponse.success(post));
    }

    // 게시글 생성 (관리자용)
    @PostMapping("/posts")
    public ResponseEntity<ApiResponse<BlogPostResponse>> createPost(
            @Valid @RequestBody BlogPostCreateRequest request) {
        BlogPostResponse post = blogPostService.createPost(request);
        return ResponseEntity.ok(ApiResponse.success(post));
    }

    // 게시글 수정 (관리자용)
    @PutMapping("/posts/{id}")
    public ResponseEntity<ApiResponse<BlogPostResponse>> updatePost(
            @PathVariable Long id,
            @Valid @RequestBody BlogPostUpdateRequest request) {
        BlogPostResponse post = blogPostService.updatePost(id, request);
        return ResponseEntity.ok(ApiResponse.success(post));
    }

    // 게시글 삭제 (관리자용)
    @DeleteMapping("/posts/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePost(@PathVariable Long id) {
        blogPostService.deletePost(id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
