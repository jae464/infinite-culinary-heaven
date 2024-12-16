package com.culinaryheaven.domain.device.service;

import com.culinaryheaven.domain.device.domain.DeviceToken;
import com.culinaryheaven.domain.device.dto.request.DeviceTokenPersistRequest;
import com.culinaryheaven.domain.device.dto.response.DeviceTokenResponse;
import com.culinaryheaven.domain.device.repository.DeviceTokenRepository;
import com.culinaryheaven.domain.user.domain.User;
import com.culinaryheaven.domain.user.repository.UserRepository;
import com.culinaryheaven.global.exception.CustomException;
import com.culinaryheaven.global.exception.ErrorCode;
import com.culinaryheaven.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeviceTokenService {

    private final DeviceTokenRepository deviceTokenRepository;
    private final UserRepository userRepository;
    private final SecurityUtil securityUtil;

    @Transactional
    public DeviceTokenResponse persist(DeviceTokenPersistRequest request) {
        User user = userRepository.findByOauthId(securityUtil.getUserOAuth2Id()).orElseThrow(() -> new CustomException(ErrorCode.AUTHORIZATION_FAILED));
        DeviceToken savedDeviceToken = deviceTokenRepository.findByUserId(user.getId()).orElseGet(
                () -> {
                    DeviceToken deviceToken = DeviceToken.builder()
                            .token(request.token())
                            .build();
                    return deviceTokenRepository.save(deviceToken);
                }
        );
        return DeviceTokenResponse.of(savedDeviceToken);
    }


}
