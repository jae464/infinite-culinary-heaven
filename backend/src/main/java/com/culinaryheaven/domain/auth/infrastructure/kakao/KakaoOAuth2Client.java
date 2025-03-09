package com.culinaryheaven.domain.auth.infrastructure.kakao;

import com.culinaryheaven.domain.auth.domain.OAuth2Type;
import com.culinaryheaven.domain.auth.OAuth2Client;
import com.culinaryheaven.domain.auth.infrastructure.dto.response.OAuth2UserInfoResponse;
import com.culinaryheaven.global.exception.CustomException;
import com.culinaryheaven.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;


@Component
@RequiredArgsConstructor
public class KakaoOAuth2Client implements OAuth2Client {

    private static final String TOKEN_TYPE = "Bearer ";
    private static final String USER_INFO_URL = "https://kapi.kakao.com/v2/user/me";

    private final RestTemplate restTemplate;

    @Override
    public OAuth2Type getOauthType() {
        return OAuth2Type.KAKAO;
    }

    @Override
    public String getOAuth2UserId(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(HttpHeaders.AUTHORIZATION, TOKEN_TYPE + accessToken);

        HttpEntity<HttpHeaders> request = new HttpEntity<>(headers);

        try {
            ResponseEntity<OAuth2UserInfoResponse> response = restTemplate.exchange(
                    USER_INFO_URL,
                    HttpMethod.GET,
                    request,
                    OAuth2UserInfoResponse.class
            );
            System.out.println(response);
            if (response.getBody() == null) {
                throw new CustomException(ErrorCode.KAKAO_INTERNAL_ERROR);
            }
            return response.getBody().id().toString();

        } catch (HttpStatusCodeException e) {
            throw new CustomException(ErrorCode.KAKAO_INTERNAL_ERROR);
        }
    }
}
