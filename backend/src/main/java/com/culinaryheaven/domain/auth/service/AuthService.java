package com.culinaryheaven.domain.auth.service;

import com.culinaryheaven.domain.auth.domain.OAuth2Type;
import com.culinaryheaven.domain.auth.domain.TokenType;
import com.culinaryheaven.domain.auth.dto.request.AdminLoginRequest;
import com.culinaryheaven.domain.auth.dto.request.ReissueRequest;
import com.culinaryheaven.domain.auth.dto.response.LoginResponse;
import com.culinaryheaven.domain.auth.dto.response.ReissueResponse;
import com.culinaryheaven.domain.auth.infrastructure.JwtTokenProvider;
import com.culinaryheaven.domain.auth.infrastructure.dto.response.OAuth2UserInfoResponse;
import com.culinaryheaven.domain.auth.infrastructure.google.GoogleOAuth2Client;
import com.culinaryheaven.domain.auth.infrastructure.kakao.KakaoOAuth2Client;
import com.culinaryheaven.domain.user.domain.User;
import com.culinaryheaven.domain.user.repository.UserRepository;
import com.culinaryheaven.domain.user.util.NickNameGenerator;
import com.culinaryheaven.global.exception.CustomException;
import com.culinaryheaven.global.exception.ErrorCode;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final KakaoOAuth2Client oAuth2Client; // todo OAuth2ClientProvider 구현후 타입 바꾸기 (OAuth2Client)
    private final GoogleOAuth2Client googleOAuth2Client;
    private final JwtTokenProvider jwtTokenProvider;
    private static final String MEMBER_ROLE_CLAIM_KEY = "memberRole";

    @Transactional
    public LoginResponse login(String oauth2Type, String oauth2AccessToken) {

        OAuth2Type oAuth2Type = OAuth2Type.from(oauth2Type);

        String oAuth2Id;

        if (oAuth2Type == OAuth2Type.KAKAO) {
            oAuth2Id = oAuth2Client.getOAuth2UserId(oauth2AccessToken);
        }
        else if (oAuth2Type == OAuth2Type.GOOGLE) {
            oAuth2Id = googleOAuth2Client.getOAuth2UserId(oauth2AccessToken);
        }
        else {
            throw new CustomException(ErrorCode.INVALID_OAUTH2_TYPE);
        }

        String accessToken = jwtTokenProvider.provideToken(oAuth2Id, TokenType.ACCESS, "ROLE_USER");
        String refreshToken = jwtTokenProvider.provideToken(oAuth2Id, TokenType.REFRESH, "ROLE_USER");

        userRepository.findByOauthId(oAuth2Id)
                .orElseGet(() -> {
                    User user = User.builder()
                            .username(NickNameGenerator.generateNickName())
                            .oauthType(oAuth2Type)
                            .oauthId(oAuth2Id)
                            .build();
                    return userRepository.save(user);
                });

        return new LoginResponse(accessToken, refreshToken);
    }

    public LoginResponse loginAsAdmin(AdminLoginRequest adminLoginRequest) {

        // todo 추후 Admin 관련 테이블 생기면 수정하기
        if (adminLoginRequest.id().equals("admin") && adminLoginRequest.password().equals("admin")) {

            String accessToken = jwtTokenProvider.provideToken("1", TokenType.ACCESS, "ROLE_ADMIN");
            String refreshToken = jwtTokenProvider.provideToken("1", TokenType.REFRESH, "ROLE_ADMIN");

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
                userRepository.findByOauthId(userId).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUNT));
                String role = claims.get(MEMBER_ROLE_CLAIM_KEY, String.class);
                String newAccessToken = jwtTokenProvider.provideToken(userId, TokenType.ACCESS, role);
                String newRefreshToken = jwtTokenProvider.provideToken(userId, TokenType.REFRESH, role);
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
