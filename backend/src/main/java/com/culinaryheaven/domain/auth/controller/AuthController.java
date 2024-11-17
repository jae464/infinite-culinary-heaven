package com.culinaryheaven.domain.auth.controller;

import com.culinaryheaven.domain.auth.dto.request.OAuth2LoginRequest;
import com.culinaryheaven.domain.auth.dto.response.LoginResponse;
import com.culinaryheaven.domain.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login/oauth2")
    public ResponseEntity<LoginResponse> oauth2Login(
            @RequestBody OAuth2LoginRequest request
    ) {
        LoginResponse loginResponse = authService.login("kakao", request.accessToken());
        return ResponseEntity.ok().body(loginResponse);
    }

}
