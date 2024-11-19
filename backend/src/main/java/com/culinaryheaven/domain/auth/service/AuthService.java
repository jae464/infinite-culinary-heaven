package com.culinaryheaven.domain.auth.service;

import com.culinaryheaven.domain.auth.domain.OAuth2Type;
import com.culinaryheaven.domain.auth.domain.TokenType;
import com.culinaryheaven.domain.auth.dto.response.LoginResponse;
import com.culinaryheaven.domain.auth.infrastructure.JwtTokenProvider;
import com.culinaryheaven.domain.auth.infrastructure.dto.response.UserInfoResponse;
import com.culinaryheaven.domain.auth.infrastructure.kakao.KakaoOAuth2Client;
import com.culinaryheaven.domain.user.domain.User;
import com.culinaryheaven.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final KakaoOAuth2Client oAuth2Client; // todo OAuth2ClientProvider 구현후 타입 바꾸기
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public LoginResponse login(String oauth2Type, String oauth2AccessToken) {

        if (OAuth2Type.from(oauth2Type) == OAuth2Type.KAKAO) {
            UserInfoResponse userInfoResponse = oAuth2Client.getUserInfo(oauth2AccessToken);
            System.out.println(userInfoResponse.toString());

            String accessToken = jwtTokenProvider.provideToken(userInfoResponse.id(), TokenType.ACCESS);
            String refreshToken = jwtTokenProvider.provideToken(userInfoResponse.id(), TokenType.REFRESH);
            System.out.println("created jwt token : " + accessToken + " " + refreshToken);

            User savedUser = userRepository.findByOauthId(userInfoResponse.id().toString())
                            .orElseGet(() -> {
                                User user = User.builder()
                                        .username(OAuth2Type.KAKAO.getValue() + "_" +  UUID.randomUUID())
                                        .oauthType(OAuth2Type.KAKAO)
                                        .oauthId(userInfoResponse.id().toString())
                                        .build();
                                return userRepository.save(user);
                            });

            System.out.println(savedUser);

            return new LoginResponse(accessToken, refreshToken);
        }
        return null;
    }
}
