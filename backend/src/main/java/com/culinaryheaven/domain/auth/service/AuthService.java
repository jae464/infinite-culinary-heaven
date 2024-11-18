package com.culinaryheaven.domain.auth.service;

import com.culinaryheaven.domain.auth.domain.OAuth2Type;
import com.culinaryheaven.domain.auth.dto.response.LoginResponse;
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

    @Transactional
    public LoginResponse login(String oauth2Type, String accessToken) {

        if (OAuth2Type.from(oauth2Type) == OAuth2Type.KAKAO) {
            UserInfoResponse userInfoResponse = oAuth2Client.getUserInfo(accessToken);
            System.out.println(userInfoResponse.toString());
            // todo userInfoResponse 가지고 JWT token 생성

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

        }
        return new LoginResponse("accessToken", "refreshToken");
    }
}
