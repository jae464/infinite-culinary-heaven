package com.culinaryheaven.global.configuration;

import com.culinaryheaven.global.annotation.Authenticated;
import com.culinaryheaven.global.security.PrincipalUserInfo;
import com.culinaryheaven.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class PrincipalUserInfoArgumentResolver implements HandlerMethodArgumentResolver {

    private final SecurityUtil securityUtil;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Authenticated.class);
    }

    @Override
    public PrincipalUserInfo resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        return new PrincipalUserInfo(securityUtil.getUserOAuth2Id());
    }
}
