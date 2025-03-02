package com.culinaryheaven.domain.device.controller;

import com.culinaryheaven.domain.device.dto.request.DeviceTokenPersistRequest;
import com.culinaryheaven.domain.device.dto.response.DeviceTokenResponse;
import com.culinaryheaven.domain.device.service.DeviceTokenService;
import com.culinaryheaven.domain.user.domain.User;
import com.culinaryheaven.global.annotation.Authenticated;
import com.culinaryheaven.global.exception.CustomException;
import com.culinaryheaven.global.exception.ErrorCode;
import com.culinaryheaven.global.security.PrincipalUserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/device-token")
public class DeviceTokenController {

    private final DeviceTokenService deviceTokenService;

    @PatchMapping
    public ResponseEntity<DeviceTokenResponse> persistDeviceToken(
            @Authenticated PrincipalUserInfo principalUserInfo,
            @RequestBody DeviceTokenPersistRequest request
    ) {
        DeviceTokenResponse deviceTokenResponse = deviceTokenService.persist(request, principalUserInfo.userId());
        return ResponseEntity.ok().body(deviceTokenResponse);
    }

}
