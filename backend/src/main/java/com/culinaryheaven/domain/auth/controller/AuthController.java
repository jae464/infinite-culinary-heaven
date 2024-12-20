package com.culinaryheaven.domain.auth.controller;

import com.culinaryheaven.domain.auth.dto.request.AdminLoginRequest;
import com.culinaryheaven.domain.auth.dto.request.OAuth2LoginRequest;
import com.culinaryheaven.domain.auth.dto.request.ReissueRequest;
import com.culinaryheaven.domain.auth.dto.response.LoginResponse;
import com.culinaryheaven.domain.auth.dto.response.ReissueResponse;
import com.culinaryheaven.domain.auth.service.AuthService;
import com.culinaryheaven.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final SecurityUtil securityUtil;

    @PostMapping("/login/oauth2")
    public ResponseEntity<LoginResponse> oauth2Login(
            @RequestBody OAuth2LoginRequest request
    ) {
        LoginResponse loginResponse = authService.login("kakao", request.accessToken());
        return ResponseEntity.ok().body(loginResponse);
    }

    @PostMapping("/login/admin")
    public ResponseEntity<LoginResponse> adminLogin(
            @RequestBody AdminLoginRequest request
    ) {
        LoginResponse loginResponse = authService.loginAsAdmin(request);
        return ResponseEntity.ok().body(loginResponse);
    }


    @PostMapping("/reissue")
    public ResponseEntity<ReissueResponse> reissue(
            @RequestBody ReissueRequest request
    ) {
        ReissueResponse reissueResponse = authService.reissue(request);
        return ResponseEntity.ok().body(reissueResponse);
    }

}
