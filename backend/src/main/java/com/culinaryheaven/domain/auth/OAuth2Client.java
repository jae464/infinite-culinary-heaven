package com.culinaryheaven.domain.auth;

import com.culinaryheaven.domain.auth.domain.OAuth2Type;

public interface OAuth2Client {

    OAuth2Type getOauthType();
    String getOAuth2UserId(String accessToken);

}
