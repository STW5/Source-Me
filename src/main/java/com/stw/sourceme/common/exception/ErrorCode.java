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
    PROFILE_ALREADY_EXISTS("PROFILE_002", "프로필이 이미 존재합니다.");

    private final String code;
    private final String message;
}
