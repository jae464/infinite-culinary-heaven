package com.culinaryheaven.global.util;

import com.culinaryheaven.global.exception.CustomException;
import com.culinaryheaven.global.exception.ErrorCode;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SecurityUtil {

    public String getUserOAuth2Id() {
        String oauth2Id = SecurityContextHolder.getContext().getAuthentication().getName();
        if (oauth2Id == null) {
            throw new CustomException(ErrorCode.AUTHORIZATION_NOT_FOUND);
        }
        return oauth2Id;
    }

}
