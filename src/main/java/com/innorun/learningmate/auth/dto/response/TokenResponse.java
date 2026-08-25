package com.innorun.learningmate.auth.dto.response;

import com.innorun.learningmate.global.security.jwt.JwtTokenPair;

public record TokenResponse(
        String accessToken,
        String refreshToken
) {

    public static TokenResponse from(JwtTokenPair tokenPair) {
        return new TokenResponse(
                tokenPair.accessToken(),
                tokenPair.refreshToken()
        );
    }
}
