package com.culinaryheaven.domain.device.controller;

import com.culinaryheaven.domain.device.dto.request.DeviceTokenPersistRequest;
import com.culinaryheaven.domain.device.dto.response.DeviceTokenResponse;
import com.culinaryheaven.domain.device.service.DeviceTokenService;
import com.culinaryheaven.domain.user.domain.User;
import com.culinaryheaven.global.exception.CustomException;
import com.culinaryheaven.global.exception.ErrorCode;
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
            @RequestBody DeviceTokenPersistRequest request
    ) {
        DeviceTokenResponse deviceTokenResponse = deviceTokenService.persist(request);
        return ResponseEntity.ok().body(deviceTokenResponse);
    }

}
