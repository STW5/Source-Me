package com.stw.sourceme.tag.service;

import com.stw.sourceme.common.exception.BusinessException;
import com.stw.sourceme.common.exception.ErrorCode;
import com.stw.sourceme.common.exception.ResourceNotFoundException;
import com.stw.sourceme.tag.controller.dto.TagCreateRequest;
import com.stw.sourceme.tag.controller.dto.TagResponse;
import com.stw.sourceme.tag.controller.dto.TagUpdateRequest;
import com.stw.sourceme.tag.entity.Tag;
import com.stw.sourceme.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TagService {

    private final TagRepository tagRepository;

    // 전체 태그 조회
    public List<TagResponse> getAllTags() {
        return tagRepository.findAll().stream()
                .map(TagResponse::from)
                .collect(Collectors.toList());
    }

    // ID로 태그 조회
    public TagResponse getTagById(Long id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.TAG_NOT_FOUND));
        return TagResponse.from(tag);
    }

    // 태그 생성
    @Transactional
    public TagResponse createTag(TagCreateRequest request) {
        if (tagRepository.existsByName(request.getName())) {
            throw new BusinessException(ErrorCode.TAG_NAME_ALREADY_EXISTS);
        }

        Tag tag = tagRepository.save(request.toEntity());
        return TagResponse.from(tag);
    }

    // 태그 수정
    @Transactional
    public TagResponse updateTag(Long id, TagUpdateRequest request) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.TAG_NOT_FOUND));

        // 태그명 중복 체크 (현재 태그 제외)
        if (!tag.getName().equals(request.getName()) &&
            tagRepository.existsByName(request.getName())) {
            throw new BusinessException(ErrorCode.TAG_NAME_ALREADY_EXISTS);
        }

        tag.update(request.getName());
        return TagResponse.from(tag);
    }

    // 태그 삭제
    @Transactional
    public void deleteTag(Long id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.TAG_NOT_FOUND));
        tagRepository.delete(tag);
    }
}
