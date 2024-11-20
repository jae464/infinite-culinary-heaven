package com.culinaryheaven.domain.auth.infrastructure;

import com.culinaryheaven.domain.auth.domain.TokenType;
import com.culinaryheaven.global.exception.CustomException;
import com.culinaryheaven.global.exception.ErrorCode;
import io.jsonwebtoken.Claims;
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

    private final SecretKey secretKey;
    private final long accessTokenValidityInMilliseconds;
    private final long refreshTokenValidityInMilliseconds;

    private static final String MEMBER_ROLE_CLAIM_KEY = "memberRole";

    public JwtTokenProvider(
            @Value("${jwt.secret}") String secretKey,
            @Value("${jwt.access-token-validity-in-seconds}") long accessTokenValidityInSeconds,
            @Value("${jwt.refresh-token-validity-in-seconds}") long refreshTokenValidityInSeconds
    ) {
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        this.accessTokenValidityInMilliseconds = accessTokenValidityInSeconds * 1000;
        this.refreshTokenValidityInMilliseconds = refreshTokenValidityInSeconds * 1000;
    }

    public String provideToken(Long id, TokenType tokenType, String role) {
        Claims claims = Jwts.claims();
        claims.put(MEMBER_ROLE_CLAIM_KEY, role);

        long now = (new Date()).getTime();

        Date validity;

        if (tokenType == TokenType.ACCESS) {
            validity = new Date(now + this.accessTokenValidityInMilliseconds);
        } else if (tokenType == TokenType.REFRESH) {
            validity = new Date(now + this.refreshTokenValidityInMilliseconds);
        } else {
            throw new CustomException(ErrorCode.INVALID_TOKEN_TYPE);
        }

        return Jwts.builder()
                .setSubject(id.toString())
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(validity)
                .signWith(this.secretKey)
                .compact();
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }





}
