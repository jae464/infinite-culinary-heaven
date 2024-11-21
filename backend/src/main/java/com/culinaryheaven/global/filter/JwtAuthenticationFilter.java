package com.culinaryheaven.global.filter;

import com.culinaryheaven.domain.auth.domain.TokenType;
import com.culinaryheaven.domain.auth.infrastructure.JwtTokenProvider;
import com.culinaryheaven.global.exception.CustomException;
import com.culinaryheaven.global.exception.dto.ExceptionResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private static final String MEMBER_ROLE_CLAIM_KEY = "memberRole";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtTokenProvider.resolveToken(request);
        System.out.println(token);

        try {
            if (token != null && jwtTokenProvider.validateAccessToken(token)) {
                Claims claims = jwtTokenProvider.getClaimsFromToken(token, TokenType.ACCESS);
                String username = claims.getSubject();
                String role = claims.get(MEMBER_ROLE_CLAIM_KEY, String.class);
                System.out.println("username : " + username);
                System.out.println(role);
                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role);
                Authentication authentication = new UsernamePasswordAuthenticationToken(username, token, List.of(grantedAuthority));
                System.out.println(authentication);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (CustomException ex) {
            System.out.println(ex.getMessage());
            response.setStatus(ex.getErrorCode().getHttpStatus().value());
            response.setContentType("application/json; charset=UTF-8");
            ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            response.getWriter().write(
                    mapper.writeValueAsString(
                            new ExceptionResponse(ex.getMessage())
                    )
            );
            return;
        }
        System.out.println("doFilter");
        filterChain.doFilter(request, response);
    }
}
