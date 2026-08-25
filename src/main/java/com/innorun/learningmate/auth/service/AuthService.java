package com.innorun.learningmate.auth.service;

import com.innorun.learningmate.auth.dto.request.LoginRequest;
import com.innorun.learningmate.auth.dto.request.ReissueRequest;
import com.innorun.learningmate.auth.dto.request.SignupRequest;
import com.innorun.learningmate.auth.dto.response.TokenResponse;
import com.innorun.learningmate.auth.exception.AuthErrorCode;
import com.innorun.learningmate.global.security.jwt.JwtTokenPair;
import com.innorun.learningmate.global.security.jwt.JwtUtil;
import com.innorun.learningmate.user.entity.User;
import com.innorun.learningmate.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public void signup(SignupRequest request) {
        if (userRepository.existsByEmailAndDeletedAtIsNull(request.email())) {
            throw AuthErrorCode.EMAIL_ALREADY_EXISTS.toException();
        }
        if (userRepository.existsByNicknameAndDeletedAtIsNull(request.nickname())) {
            throw AuthErrorCode.NICKNAME_ALREADY_EXISTS.toException();
        }

        User user = new User(
                request.email(),
                passwordEncoder.encode(request.password()),
                request.nickname()
        );

        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public TokenResponse login(LoginRequest request) {
        User user = userRepository.findByEmailAndDeletedAtIsNull(request.email())
                .orElseThrow(AuthErrorCode.INVALID_CREDENTIALS::toException);

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw AuthErrorCode.INVALID_CREDENTIALS.toException();
        }

        return TokenResponse.from(jwtUtil.issueTokenPair(user));
    }

    @Transactional(readOnly = true)
    public TokenResponse reissue(ReissueRequest request) {
        Claims claims = jwtUtil.getRefreshTokenClaims(request.refreshToken());
        Long userId = parseUserId(claims.getSubject());
        User user = userRepository.findByIdAndDeletedAtIsNull(userId)
                .orElseThrow(AuthErrorCode.INVALID_REFRESH_TOKEN::toException);

        JwtTokenPair tokenPair = jwtUtil.issueTokenPair(user);
        return TokenResponse.from(tokenPair);
    }

    private Long parseUserId(String subject) {
        try {
            return Long.valueOf(subject);
        } catch (NumberFormatException | NullPointerException exception) {
            throw AuthErrorCode.INVALID_REFRESH_TOKEN.toException();
        }
    }
}
