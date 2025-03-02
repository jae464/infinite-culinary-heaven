package com.culinaryheaven.global.util;

import com.culinaryheaven.global.exception.CustomException;
import com.culinaryheaven.global.exception.ErrorCode;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SecurityUtil {

    public Long getUserId() {
        String userId  = SecurityContextHolder.getContext().getAuthentication().getName();
        if (userId == null) {
            throw new CustomException(ErrorCode.AUTHORIZATION_NOT_FOUND);
        }
        Long parsedUserId = Long.parseLong(userId);
        return parsedUserId;
    }

}
