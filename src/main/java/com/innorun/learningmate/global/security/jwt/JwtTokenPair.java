package com.innorun.learningmate.global.security.jwt;

public record JwtTokenPair(
        String accessToken,
        String refreshToken
) {
}