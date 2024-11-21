package com.culinaryheaven.domain.auth.infrastructure;

import com.culinaryheaven.domain.auth.domain.TokenType;
import com.culinaryheaven.global.exception.CustomException;
import com.culinaryheaven.global.exception.ErrorCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final SecretKey accessSecretKey;
    private final SecretKey refreshSecretKey;
    private final long accessTokenValidityInMilliseconds;
    private final long refreshTokenValidityInMilliseconds;

    private static final String MEMBER_ROLE_CLAIM_KEY = "memberRole";

    public JwtTokenProvider(
            @Value("${jwt.secret-access}") String accessSecretKey,
            @Value("${jwt.secret-refresh}") String refreshSecretKey,
            @Value("${jwt.access-token-validity-in-seconds}") long accessTokenValidityInSeconds,
            @Value("${jwt.refresh-token-validity-in-seconds}") long refreshTokenValidityInSeconds
    ) {
        this.accessSecretKey = Keys.hmacShaKeyFor(accessSecretKey.getBytes(StandardCharsets.UTF_8));
        this.refreshSecretKey = Keys.hmacShaKeyFor(refreshSecretKey.getBytes(StandardCharsets.UTF_8));
        this.accessTokenValidityInMilliseconds = accessTokenValidityInSeconds * 1000;
        this.refreshTokenValidityInMilliseconds = refreshTokenValidityInSeconds * 1000;
    }

    public String provideToken(Long id, TokenType tokenType, String role) {
        long now = (new Date()).getTime();

        Date validity;
        SecretKey secretKey;

        if (tokenType == TokenType.ACCESS) {
            validity = new Date(now + this.accessTokenValidityInMilliseconds);
            secretKey = accessSecretKey;
        } else if (tokenType == TokenType.REFRESH) {
            validity = new Date(now + this.refreshTokenValidityInMilliseconds);
            secretKey = refreshSecretKey;
        } else {
            throw new CustomException(ErrorCode.INVALID_TOKEN_TYPE);
        }

        return Jwts.builder()
                .setSubject(id.toString())
                .claim(MEMBER_ROLE_CLAIM_KEY, role)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(validity)
                .signWith(secretKey)
                .compact();
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateAccessToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey(TokenType.ACCESS)).build().parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            throw new CustomException(ErrorCode.ACCESS_TOKEN_EXPIRED);
        } catch (JwtException | IllegalArgumentException e) {
            throw new CustomException(ErrorCode.AUTHORIZATION_FAILED);
        }
    }

    public boolean validateRefreshToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey(TokenType.REFRESH)).build().parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            throw new CustomException(ErrorCode.REFRESH_TOKEN_EXPIRED);
        } catch (JwtException | IllegalArgumentException e) {
            throw new CustomException(ErrorCode.AUTHORIZATION_FAILED);
        }
    }

    public Claims getClaimsFromToken(String token, TokenType tokenType) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey(tokenType))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private SecretKey getSigningKey(TokenType tokenType) {
        if (tokenType == TokenType.ACCESS) {
            return this.accessSecretKey;
        } else if (tokenType == TokenType.REFRESH) {
            return this.refreshSecretKey;
        } else {
            throw new CustomException(ErrorCode.INVALID_TOKEN_TYPE);
        }
    }

}
