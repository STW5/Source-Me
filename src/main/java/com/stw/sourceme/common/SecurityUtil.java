package com.stw.sourceme.common;

import com.stw.sourceme.auth.dto.CustomUserDetails;
import com.stw.sourceme.common.exception.BusinessException;
import com.stw.sourceme.common.exception.ErrorCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    private SecurityUtil() {
        // Utility class
    }

    /**
     * 현재 인증된 사용자의 ID를 반환합니다.
     *
     * @return 현재 사용자의 ID
     * @throws BusinessException 인증되지 않은 경우
     */
    public static Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new BusinessException(ErrorCode.UNAUTHORIZED, "인증되지 않은 사용자입니다.");
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof CustomUserDetails) {
            return ((CustomUserDetails) principal).getUserId();
        }

        throw new BusinessException(ErrorCode.UNAUTHORIZED, "인증 정보를 찾을 수 없습니다.");
    }

    /**
     * 현재 인증된 사용자의 username을 반환합니다.
     *
     * @return 현재 사용자의 username
     * @throws BusinessException 인증되지 않은 경우
     */
    public static String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new BusinessException(ErrorCode.UNAUTHORIZED, "인증되지 않은 사용자입니다.");
        }

        return authentication.getName();
    }
}
