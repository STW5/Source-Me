package com.stw.sourceme.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // Common
    INTERNAL_SERVER_ERROR("COMMON_001", "서버 내부 오류가 발생했습니다."),
    INVALID_INPUT_VALUE("COMMON_002", "잘못된 입력값입니다."),

    // Profile
    PROFILE_NOT_FOUND("PROFILE_001", "프로필 정보가 존재하지 않습니다."),
    PROFILE_ALREADY_EXISTS("PROFILE_002", "프로필이 이미 존재합니다."),

    // Project
    PROJECT_NOT_FOUND("PROJECT_001", "프로젝트를 찾을 수 없습니다."),
    PROJECT_SLUG_ALREADY_EXISTS("PROJECT_002", "이미 존재하는 슬러그입니다."),

    // BlogPost
    BLOG_POST_NOT_FOUND("BLOG_001", "블로그 게시글을 찾을 수 없습니다."),
    BLOG_POST_SLUG_ALREADY_EXISTS("BLOG_002", "이미 존재하는 슬러그입니다."),

    // Auth
    INVALID_CREDENTIALS("AUTH_001", "아이디 또는 비밀번호가 올바르지 않습니다."),
    USER_DISABLED("AUTH_002", "비활성화된 계정입니다."),
    UNAUTHORIZED("AUTH_003", "인증이 필요합니다."),
    INVALID_TOKEN("AUTH_004", "유효하지 않은 토큰입니다.");

    private final String code;
    private final String message;
}
