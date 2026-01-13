package com.stw.sourceme.blog.controller;

import com.stw.sourceme.blog.controller.dto.BlogPostCreateRequest;
import com.stw.sourceme.blog.controller.dto.BlogPostListResponse;
import com.stw.sourceme.blog.controller.dto.BlogPostResponse;
import com.stw.sourceme.blog.controller.dto.BlogPostUpdateRequest;
import com.stw.sourceme.blog.service.BlogPostService;
import com.stw.sourceme.common.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/blog")
@RequiredArgsConstructor
public class BlogPostController {

    private final BlogPostService blogPostService;

    // 공개된 게시글 목록 조회 (public)
    @GetMapping("/posts")
    public ResponseEntity<ApiResponse<Page<BlogPostListResponse>>> getPublishedPosts(
            @RequestParam(required = false) String tag,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<BlogPostListResponse> posts;
        if (tag != null && !tag.isEmpty()) {
            posts = blogPostService.getPostsByTag(tag, pageable);
        } else {
            posts = blogPostService.getAllPublishedPosts(pageable);
        }
        return ResponseEntity.ok(ApiResponse.success(posts));
    }

    /**
     * 블로그 포스트 검색 API (페이징 지원)
     * @param keyword 검색 키워드 (제목, 요약, 내용, 태그에서 검색)
     * @param page 페이지 번호 (0부터 시작, 기본값: 0)
     * @param size 페이지 크기 (기본값: 10)
     * @param sortBy 정렬 기준 (기본값: createdAt)
     * @param sortDir 정렬 방향 (asc/desc, 기본값: desc)
     * @return 검색된 블로그 포스트 페이지
     */
    @GetMapping("/posts/search")
    public ResponseEntity<ApiResponse<Page<BlogPostListResponse>>> searchPosts(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<BlogPostListResponse> posts = blogPostService.searchPublishedPosts(keyword, pageable);
        return ResponseEntity.ok(ApiResponse.success(posts));
    }

    /**
     * 인기 게시글 조회 (조회수 기준)
     * @param page 페이지 번호 (0부터 시작, 기본값: 0)
     * @param size 페이지 크기 (기본값: 10)
     * @return 조회수가 높은 게시글 목록
     */
    @GetMapping("/posts/popular")
    public ResponseEntity<ApiResponse<Page<BlogPostListResponse>>> getPopularPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<BlogPostListResponse> posts = blogPostService.getPopularPosts(pageable);
        return ResponseEntity.ok(ApiResponse.success(posts));
    }

    /**
     * 좋아요가 많은 게시글 조회
     * @param page 페이지 번호 (0부터 시작, 기본값: 0)
     * @param size 페이지 크기 (기본값: 10)
     * @return 좋아요가 많은 게시글 목록
     */
    @GetMapping("/posts/most-liked")
    public ResponseEntity<ApiResponse<Page<BlogPostListResponse>>> getMostLikedPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<BlogPostListResponse> posts = blogPostService.getMostLikedPosts(pageable);
        return ResponseEntity.ok(ApiResponse.success(posts));
    }

    // 모든 게시글 목록 조회 (관리자용)
    @GetMapping("/admin/posts")
    public ResponseEntity<ApiResponse<Page<BlogPostListResponse>>> getAllPosts(
            @RequestParam(required = false) String tag,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<BlogPostListResponse> posts;
        if (tag != null && !tag.isEmpty()) {
            posts = blogPostService.getAllPostsByTag(tag, pageable);
        } else {
            posts = blogPostService.getAllPosts(pageable);
        }
        return ResponseEntity.ok(ApiResponse.success(posts));
    }

    // ID로 게시글 조회
    @GetMapping("/posts/{id}")
    public ResponseEntity<ApiResponse<BlogPostResponse>> getPostById(@PathVariable UUID id) {
        BlogPostResponse post = blogPostService.getPostById(id);
        return ResponseEntity.ok(ApiResponse.success(post));
    }

    // 조회수 증가
    @PostMapping("/posts/{id}/view")
    public ResponseEntity<ApiResponse<Void>> incrementViewCount(@PathVariable UUID id) {
        blogPostService.incrementViewCount(id);
        return ResponseEntity.ok(ApiResponse.success(null));
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
            @PathVariable UUID id,
            @Valid @RequestBody BlogPostUpdateRequest request) {
        BlogPostResponse post = blogPostService.updatePost(id, request);
        return ResponseEntity.ok(ApiResponse.success(post));
    }

    // 게시글 삭제 (관리자용)
    @DeleteMapping("/posts/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePost(@PathVariable UUID id) {
        blogPostService.deletePost(id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
