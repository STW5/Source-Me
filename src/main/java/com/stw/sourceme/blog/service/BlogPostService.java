package com.stw.sourceme.blog.service;

import com.stw.sourceme.blog.controller.dto.BlogPostCreateRequest;
import com.stw.sourceme.blog.controller.dto.BlogPostListResponse;
import com.stw.sourceme.blog.controller.dto.BlogPostResponse;
import com.stw.sourceme.blog.controller.dto.BlogPostUpdateRequest;
import com.stw.sourceme.blog.entity.BlogPost;
import com.stw.sourceme.blog.repository.BlogPostRepository;
import com.stw.sourceme.common.exception.BusinessException;
import com.stw.sourceme.common.exception.ErrorCode;
import com.stw.sourceme.common.exception.ResourceNotFoundException;
import com.stw.sourceme.media.entity.MediaFile;
import com.stw.sourceme.media.repository.MediaFileRepository;
import com.stw.sourceme.tag.entity.Tag;
import com.stw.sourceme.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BlogPostService {

    private final BlogPostRepository blogPostRepository;
    private final TagRepository tagRepository;
    private final MediaFileRepository mediaFileRepository;

    // 공개된 게시글만 조회 (public endpoint용, 페이징)
    public Page<BlogPostListResponse> getAllPublishedPosts(Pageable pageable) {
        return blogPostRepository.findAllPublishedPosts(pageable)
                .map(BlogPostListResponse::from);
    }

    // 모든 게시글 조회 (관리자용, 페이징)
    public Page<BlogPostListResponse> getAllPosts(Pageable pageable) {
        return blogPostRepository.findAllPosts(pageable)
                .map(BlogPostListResponse::from);
    }

    // 태그별 게시글 조회 (public, 페이징)
    public Page<BlogPostListResponse> getPostsByTag(String tagName, Pageable pageable) {
        return blogPostRepository.findPublishedPostsByTag(tagName, pageable)
                .map(BlogPostListResponse::from);
    }

    // 태그별 게시글 조회 (관리자용, 페이징)
    public Page<BlogPostListResponse> getAllPostsByTag(String tagName, Pageable pageable) {
        return blogPostRepository.findAllPostsByTag(tagName, pageable)
                .map(BlogPostListResponse::from);
    }

    /**
     * 블로그 포스트 검색 (페이징 지원)
     * @param keyword 검색 키워드 (null 또는 빈 문자열이면 전체 조회)
     * @param pageable 페이징 정보
     * @return 검색된 공개 블로그 포스트 페이지
     */
    public Page<BlogPostListResponse> searchPublishedPosts(String keyword, Pageable pageable) {
        Page<BlogPost> postPage = blogPostRepository.searchPublishedBlogPosts(keyword, pageable);
        return postPage.map(BlogPostListResponse::from);
    }

    /**
     * 모든 블로그 포스트 검색 (관리자용, 페이징 지원)
     * @param keyword 검색 키워드 (null 또는 빈 문자열이면 전체 조회)
     * @param pageable 페이징 정보
     * @return 검색된 모든 블로그 포스트 페이지
     */
    public Page<BlogPostListResponse> searchAllPosts(String keyword, Pageable pageable) {
        Page<BlogPost> postPage = blogPostRepository.searchBlogPosts(keyword, pageable);
        return postPage.map(BlogPostListResponse::from);
    }

    // ID로 조회 (조회수 증가 안함)
    public BlogPostResponse getPostById(UUID id) {
        BlogPost blogPost = blogPostRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.BLOG_POST_NOT_FOUND));
        return BlogPostResponse.from(blogPost);
    }

    // 조회수 증가
    @Transactional
    public void incrementViewCount(UUID id) {
        BlogPost blogPost = blogPostRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.BLOG_POST_NOT_FOUND));
        blogPost.incrementViewCount();
    }

    // 인기 게시글 조회 (조회수 기준)
    public Page<BlogPostListResponse> getPopularPosts(Pageable pageable) {
        Page<BlogPost> postPage = blogPostRepository.findPopularPublishedPosts(pageable);
        return postPage.map(BlogPostListResponse::from);
    }

    // 좋아요가 많은 게시글 조회
    public Page<BlogPostListResponse> getMostLikedPosts(Pageable pageable) {
        Page<BlogPost> postPage = blogPostRepository.findMostLikedPublishedPosts(pageable);
        return postPage.map(BlogPostListResponse::from);
    }

    // 게시글 생성
    @Transactional
    public BlogPostResponse createPost(BlogPostCreateRequest request) {
        BlogPost blogPost = request.toEntity();

        // 썸네일 설정
        MediaFile thumbnailMedia = resolveThumbnailMedia(request.getThumbnailMediaId());
        blogPost.update(
                blogPost.getTitle(),
                blogPost.getSummary(),
                blogPost.getContentMarkdown(),
                blogPost.getStatus(),
                thumbnailMedia
        );

        // 태그 연결 (없으면 자동 생성)
        if (request.getTagNames() != null && !request.getTagNames().isEmpty()) {
            List<Tag> tags = getOrCreateTags(request.getTagNames());
            blogPost.updateTags(tags);
        }

        blogPost = blogPostRepository.save(blogPost);
        return BlogPostResponse.from(blogPost);
    }

    private List<Tag> getOrCreateTags(List<String> tagNames) {
        List<Tag> tags = new ArrayList<>();
        for (String tagName : tagNames) {
            String trimmedName = tagName.trim();
            if (!trimmedName.isEmpty()) {
                Tag tag = tagRepository.findByName(trimmedName)
                        .orElseGet(() -> tagRepository.save(Tag.builder().name(trimmedName).build()));
                tags.add(tag);
            }
        }
        return tags;
    }

    // 게시글 수정
    @Transactional
    public BlogPostResponse updatePost(UUID id, BlogPostUpdateRequest request) {
        BlogPost blogPost = blogPostRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.BLOG_POST_NOT_FOUND));

        // 썸네일 설정
        MediaFile thumbnailMedia = resolveThumbnailMedia(request.getThumbnailMediaId());

        blogPost.update(
                request.getTitle(),
                request.getSummary(),
                request.getContentMarkdown(),
                request.getStatus(),
                thumbnailMedia
        );

        // 태그 업데이트 (없으면 자동 생성)
        if (request.getTagNames() != null) {
            List<Tag> tags = getOrCreateTags(request.getTagNames());
            blogPost.updateTags(tags);
        }

        return BlogPostResponse.from(blogPost);
    }

    // 게시글 삭제
    @Transactional
    public void deletePost(UUID id) {
        BlogPost blogPost = blogPostRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.BLOG_POST_NOT_FOUND));
        blogPostRepository.delete(blogPost);
    }

    private MediaFile resolveThumbnailMedia(Long thumbnailMediaId) {
        if (thumbnailMediaId == null) {
            return null;
        }
        return mediaFileRepository.findById(thumbnailMediaId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEDIA_NOT_FOUND));
    }
}
