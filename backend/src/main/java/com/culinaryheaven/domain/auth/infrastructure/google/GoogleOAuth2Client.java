package com.culinaryheaven.domain.auth.infrastructure.google;

import com.culinaryheaven.domain.auth.domain.OAuth2Type;
import com.culinaryheaven.domain.auth.infrastructure.OAuth2Client;
import com.culinaryheaven.domain.auth.infrastructure.dto.response.OAuth2UserInfoResponse;
import com.culinaryheaven.global.exception.CustomException;
import com.culinaryheaven.global.exception.ErrorCode;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;

@Slf4j
@Component
@RequiredArgsConstructor
public class GoogleOAuth2Client implements OAuth2Client {

    @Value("${oauth.google.clientId}")
    private String clientId;

    @Override
    public OAuth2Type getOauthType() {
        return OAuth2Type.GOOGLE;
    }

    @Override
    public String getOAuth2UserId(String accessToken) {
        GoogleIdTokenVerifier idTokenVerifier = new GoogleIdTokenVerifier.Builder(
                new NetHttpTransport(), GsonFactory.getDefaultInstance()
        )
                .setAudience(Collections.singletonList(clientId))
                .build();
        try {
            GoogleIdToken idToken = idTokenVerifier.verify(accessToken);
            return idToken.getPayload().getSubject();
        } catch (GeneralSecurityException | IOException e) {
            throw new CustomException(ErrorCode.GOOGLE_INTERNAL_ERROR);
        }
    }
}
