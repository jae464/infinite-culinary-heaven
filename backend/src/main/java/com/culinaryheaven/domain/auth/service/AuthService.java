package com.culinaryheaven.domain.auth.service;

import com.culinaryheaven.domain.auth.domain.OAuth2Type;
import com.culinaryheaven.domain.auth.domain.TokenType;
import com.culinaryheaven.domain.auth.dto.request.AdminLoginRequest;
import com.culinaryheaven.domain.auth.dto.request.ReissueRequest;
import com.culinaryheaven.domain.auth.dto.response.LoginResponse;
import com.culinaryheaven.domain.auth.dto.response.ReissueResponse;
import com.culinaryheaven.domain.auth.infrastructure.JwtTokenProvider;
import com.culinaryheaven.domain.auth.infrastructure.dto.response.UserInfoResponse;
import com.culinaryheaven.domain.auth.infrastructure.kakao.KakaoOAuth2Client;
import com.culinaryheaven.domain.user.domain.User;
import com.culinaryheaven.domain.user.repository.UserRepository;
import com.culinaryheaven.global.exception.CustomException;
import com.culinaryheaven.global.exception.ErrorCode;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final KakaoOAuth2Client oAuth2Client; // todo OAuth2ClientProvider 구현후 타입 바꾸기 (OAuth2Client)
    private final JwtTokenProvider jwtTokenProvider;
    private static final String MEMBER_ROLE_CLAIM_KEY = "memberRole";

    @Transactional
    public LoginResponse login(String oauth2Type, String oauth2AccessToken) {

        if (OAuth2Type.from(oauth2Type) == OAuth2Type.KAKAO) {
            UserInfoResponse userInfoResponse = oAuth2Client.getUserInfo(oauth2AccessToken);

            String accessToken = jwtTokenProvider.provideToken(userInfoResponse.id(), TokenType.ACCESS, "ROLE_USER");
            String refreshToken = jwtTokenProvider.provideToken(userInfoResponse.id(), TokenType.REFRESH, "ROLE_USER");

            User savedUser = userRepository.findByOauthId(userInfoResponse.id().toString())
                            .orElseGet(() -> {
                                User user = User.builder()
                                        .username(OAuth2Type.KAKAO.getValue() + "_" +  UUID.randomUUID())
                                        .oauthType(OAuth2Type.KAKAO)
                                        .oauthId(userInfoResponse.id().toString())
                                        .build();
                                return userRepository.save(user);
                            });

            return new LoginResponse(accessToken, refreshToken);
        }
        return null;
    }

    public LoginResponse loginAsAdmin(AdminLoginRequest adminLoginRequest) {

        // todo 추후 Admin 관련 테이블 생기면 수정하기
        if (adminLoginRequest.id().equals("admin") && adminLoginRequest.password().equals("admin")) {

            String accessToken = jwtTokenProvider.provideToken(1L, TokenType.ACCESS, "ROLE_ADMIN");
            String refreshToken = jwtTokenProvider.provideToken(1L, TokenType.REFRESH, "ROLE_ADMIN");

            return new LoginResponse(accessToken, refreshToken);
        }
        else {
            throw new CustomException(ErrorCode.AUTHORIZATION_FAILED);
        }

    }

    public ReissueResponse reissue(ReissueRequest request) {
        try {
            if (jwtTokenProvider.validateRefreshToken(request.refreshToken())) {
                Claims claims = jwtTokenProvider.getClaimsFromToken(request.refreshToken(), TokenType.REFRESH);
                String userId = claims.getSubject();
                String role = claims.get(MEMBER_ROLE_CLAIM_KEY, String.class);
                String newAccessToken = jwtTokenProvider.provideToken(Long.parseLong(userId), TokenType.ACCESS, role);
                String newRefreshToken = jwtTokenProvider.provideToken(Long.parseLong(userId), TokenType.REFRESH, role);
                return new ReissueResponse(newAccessToken, newRefreshToken);
            }
            else {
                throw new CustomException(ErrorCode.AUTHORIZATION_FAILED);
            }
        } catch (Exception e) {
            throw new CustomException(ErrorCode.AUTHORIZATION_FAILED);
        }
    }
}
