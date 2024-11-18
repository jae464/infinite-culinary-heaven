package com.culinaryheaven.domain.auth.infrastructure;

import com.culinaryheaven.domain.auth.domain.TokenType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class TokenProvider {

    private final SecretKey secretKey;
    private final long accessTokenValidityInMilliseconds;
    private final long refreshTokenValidityInMilliseconds;

    private String MEMBER_ID_CLAIM_KEY = "memberId";
    private String MEMBER_ROLE_CLAIM_KEY = "memberRole";

    public TokenProvider(
            @Value("${jwt.secret}") String secretKey,
            @Value("${jwt.access-token-validity-in-seconds}") long accessTokenValidityInSeconds,
            @Value("${jwt.refresh-token-validity-in-seconds}") long refreshTokenValidityInSeconds
    ) {
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        this.accessTokenValidityInMilliseconds = accessTokenValidityInSeconds * 1000;
        this.refreshTokenValidityInMilliseconds = refreshTokenValidityInSeconds * 1000;
    }

    public String provideToken(Long id, TokenType tokenType) {
        Claims claims = Jwts.claims();
        claims.put(MEMBER_ROLE_CLAIM_KEY, "ROLE_USER");

        long now = (new Date()).getTime();

        Date validity;

        if (tokenType == TokenType.ACCESS) {
            validity = new Date(now + this.accessTokenValidityInMilliseconds);
        } else if (tokenType == TokenType.REFRESH) {
            validity = new Date(now + this.refreshTokenValidityInMilliseconds);
        } else {
            throw new IllegalArgumentException("Invalid token type");
        }

        return Jwts.builder()
                .setSubject(id.toString())
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(validity)
                .signWith(this.secretKey)
                .compact();

    }



}
