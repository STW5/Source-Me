package com.stw.sourceme.auth.application;

import com.stw.sourceme.auth.presentation.dto.LoginRequest;
import com.stw.sourceme.auth.presentation.dto.LoginResponse;
import com.stw.sourceme.auth.domain.User;
import com.stw.sourceme.auth.infrastructure.repository.UserRepository;
import com.stw.sourceme.auth.infrastructure.JwtUtil;
import com.stw.sourceme.common.exception.BusinessException;
import com.stw.sourceme.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_CREDENTIALS));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BusinessException(ErrorCode.INVALID_CREDENTIALS);
        }

        if (!user.getEnabled()) {
            throw new BusinessException(ErrorCode.USER_DISABLED);
        }

        String token = jwtUtil.generateToken(user.getUsername());
        return new LoginResponse(token, user.getUsername());
    }
}
