package com.innorun.learningmate.global.security.jwt;

import com.innorun.learningmate.user.entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtUtil {

    private final JwtProperties jwtProperties;
    private final SecretKey secretKey;

    public JwtUtil(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;

        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.secret());
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public static final String TOKEN_TYPE_ACCESS = "access";
    public static final String TOKEN_TYPE_REFRESH = "refresh";

    public static final String TOKEN_TYPE_CLAIM = "type";
    public static final String USER_ROLE_CLAIM = "role";

    public JwtTokenPair issueTokenPair(User user) {
        String accessToken = createToken(user, TOKEN_TYPE_ACCESS, jwtProperties.expire().access(), true);
        String refreshToken = createToken(user, TOKEN_TYPE_REFRESH, jwtProperties.expire().refresh(), false);

        return new JwtTokenPair(accessToken, refreshToken);
    }

    private String createToken(User user, String tokenType, Duration tokenExpired, boolean includeRole) {
        long now = System.currentTimeMillis();

        JwtBuilder builder = Jwts.builder()
                .subject(user.getId().toString()) // sub
                .id(UUID.randomUUID().toString()) // jti
                .claim(TOKEN_TYPE_CLAIM, tokenType)
                .issuedAt(new Date(now)) // iat
                .expiration(new Date(now + tokenExpired.toMillis())); // exp

        if (includeRole) {
            builder.claim(USER_ROLE_CLAIM, user.getRole().name());
        }

        return builder
                .signWith(secretKey)
                .compact();
    }
}
