package com.culinaryheaven.domain.auth.infrastructure;

import com.culinaryheaven.domain.auth.domain.OAuth2Type;
import com.culinaryheaven.domain.auth.infrastructure.dto.response.UserInfoResponse;

public interface OAuth2Client {

    OAuth2Type getOauthType();
    UserInfoResponse getUserInfo(String accessToken);

}
