package com.culinaryheaven.domain.auth.service;

import com.culinaryheaven.domain.auth.domain.OAuth2Type;
import com.culinaryheaven.domain.auth.dto.response.LoginResponse;
import com.culinaryheaven.domain.auth.infrastructure.dto.response.UserInfoResponse;
import com.culinaryheaven.domain.auth.infrastructure.kakao.KakaoOAuth2Client;
import com.culinaryheaven.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final KakaoOAuth2Client oAuth2Client; // todo OAuth2ClientProvider 구현후 타입 바꾸기

    public LoginResponse login(String oauth2Type, String accessToken) {
        // 일단 kakao 인 경우
        if (OAuth2Type.from(oauth2Type) == OAuth2Type.KAKAO) {
            UserInfoResponse userInfoResponse = oAuth2Client.getUserInfo(accessToken);
            System.out.println(userInfoResponse.toString());
            // todo userInfoResponse 가지고 JWT token 생성
        }
        return new LoginResponse("accessToken", "refreshToken");
    }
}
