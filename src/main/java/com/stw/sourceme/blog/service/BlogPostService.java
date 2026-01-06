package com.stw.sourceme.blog.service;

import com.stw.sourceme.blog.controller.dto.BlogPostCreateRequest;
import com.stw.sourceme.blog.controller.dto.BlogPostListResponse;
import com.stw.sourceme.blog.controller.dto.BlogPostResponse;
import com.stw.sourceme.blog.controller.dto.BlogPostUpdateRequest;
import com.stw.sourceme.blog.entity.BlogPost;
import com.stw.sourceme.blog.repository.BlogPostRepository;
import com.stw.sourceme.common.exception.ErrorCode;
import com.stw.sourceme.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BlogPostService {

    private final BlogPostRepository blogPostRepository;

    // 공개된 게시글만 조회 (public endpoint용)
    public List<BlogPostListResponse> getAllPublishedPosts() {
        return blogPostRepository.findAll().stream()
                .filter(post -> "PUBLISHED".equals(post.getStatus()))
                .map(BlogPostListResponse::from)
                .collect(Collectors.toList());
    }

    // 모든 게시글 조회 (관리자용)
    public List<BlogPostListResponse> getAllPosts() {
        return blogPostRepository.findAll().stream()
                .map(BlogPostListResponse::from)
                .collect(Collectors.toList());
    }

    // ID로 조회
    public BlogPostResponse getPostById(UUID id) {
        BlogPost blogPost = blogPostRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.BLOG_POST_NOT_FOUND));
        return BlogPostResponse.from(blogPost);
    }

    // 게시글 생성
    @Transactional
    public BlogPostResponse createPost(BlogPostCreateRequest request) {
        BlogPost blogPost = blogPostRepository.save(request.toEntity());
        return BlogPostResponse.from(blogPost);
    }

    // 게시글 수정
    @Transactional
    public BlogPostResponse updatePost(UUID id, BlogPostUpdateRequest request) {
        BlogPost blogPost = blogPostRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.BLOG_POST_NOT_FOUND));

        blogPost.update(
                request.getTitle(),
                request.getSummary(),
                request.getContentMarkdown(),
                request.getStatus()
        );

        return BlogPostResponse.from(blogPost);
    }

    // 게시글 삭제
    @Transactional
    public void deletePost(UUID id) {
        BlogPost blogPost = blogPostRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.BLOG_POST_NOT_FOUND));
        blogPostRepository.delete(blogPost);
    }
}
