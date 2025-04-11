package com.culinaryheaven.domain.auth.service;

import com.culinaryheaven.domain.auth.OAuth2Client;
import com.culinaryheaven.domain.auth.OAuth2ClientProvider;
import com.culinaryheaven.domain.auth.domain.OAuth2Type;
import com.culinaryheaven.domain.auth.domain.TokenType;
import com.culinaryheaven.domain.auth.dto.request.AdminLoginRequest;
import com.culinaryheaven.domain.auth.dto.request.ReissueRequest;
import com.culinaryheaven.domain.auth.dto.response.LoginResponse;
import com.culinaryheaven.domain.auth.dto.response.ReissueResponse;
import com.culinaryheaven.domain.auth.infrastructure.JwtTokenProvider;
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

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final OAuth2ClientProvider oAuth2ClientProvider;
    private final JwtTokenProvider jwtTokenProvider;
    private static final String MEMBER_ROLE_CLAIM_KEY = "memberRole";

    @Transactional
    public LoginResponse login(String oauth2Type, String oauth2AccessToken) {

        OAuth2Type oAuth2Type = OAuth2Type.from(oauth2Type);
        OAuth2Client oAuth2Client = oAuth2ClientProvider.getClient(oAuth2Type);
        String oAuth2Id = oAuth2Client.getOAuth2UserId(oauth2AccessToken);

        User savedUser = userRepository.findByOauthId(oAuth2Id)
                .orElseGet(() -> {
                    User user = User.builder()
                            .username(NickNameGenerator.generateNickName())
                            .oauthType(oAuth2Type)
                            .oauthId(oAuth2Id)
                            .build();
                    return userRepository.save(user);
                });

        String accessToken = jwtTokenProvider.provideToken(savedUser.getId().toString(), TokenType.ACCESS, "ROLE_USER");
        String refreshToken = jwtTokenProvider.provideToken(savedUser.getId().toString(), TokenType.REFRESH, "ROLE_USER");

        return new LoginResponse(accessToken, refreshToken);
    }

    public LoginResponse loginAsAdmin(AdminLoginRequest adminLoginRequest) {

        // todo 추후 Admin 관련 테이블 생기면 수정하기
        if (adminLoginRequest.id().equals("admin") && adminLoginRequest.password().equals("admin")) {

            String accessToken = jwtTokenProvider.provideToken("admin", TokenType.ACCESS, "ROLE_ADMIN");
            String refreshToken = jwtTokenProvider.provideToken("admin", TokenType.REFRESH, "ROLE_ADMIN");

            return new LoginResponse(accessToken, refreshToken);
        }
        else {
            throw new CustomException(ErrorCode.AUTHORIZATION_FAILED);
        }

    }

    @Transactional(readOnly = true)
    public ReissueResponse reissue(ReissueRequest request) {
        try {
            if (jwtTokenProvider.validateRefreshToken(request.refreshToken())) {
                Claims claims = jwtTokenProvider.getClaimsFromToken(request.refreshToken(), TokenType.REFRESH);
                String role = claims.get(MEMBER_ROLE_CLAIM_KEY, String.class);

                // todo admin 리프레쉬 토큰
                if (role.equals("ROLE_ADMIN")) {
                    String newAccessToken = jwtTokenProvider.provideToken("admin", TokenType.ACCESS, "ROLE_ADMIN");
                    String newRefreshToken = jwtTokenProvider.provideToken("admin", TokenType.REFRESH, "ROLE_ADMIN");
                    return new ReissueResponse(newAccessToken, newRefreshToken);
                }

                String userId = claims.getSubject();
                Long paredUserId = Long.parseLong(userId);
                userRepository.findById(paredUserId).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUNT));
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


