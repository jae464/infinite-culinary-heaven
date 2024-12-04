package com.culinaryheaven.domain.auth.infrastructure;

import com.culinaryheaven.domain.auth.domain.OAuth2Type;
import com.culinaryheaven.domain.auth.infrastructure.dto.response.OAuth2UserInfoResponse;

public interface OAuth2Client {

    OAuth2Type getOauthType();
    OAuth2UserInfoResponse getUserInfo(String accessToken);

}
