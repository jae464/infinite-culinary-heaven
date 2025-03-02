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
import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.FieldReflectionArbitraryIntrospector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeviceTokenServiceTest {

    @Mock
    private DeviceTokenRepository deviceTokenRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SecurityUtil securityUtil;

    @InjectMocks
    private DeviceTokenService deviceTokenService;

    private FixtureMonkey fixtureMonkey;

    @BeforeEach
    void setUp() {
        fixtureMonkey = FixtureMonkey.builder()
                .objectIntrospector(FieldReflectionArbitraryIntrospector.INSTANCE)
                .defaultNotNull(true)
                .build();
    }

    @Test
    void 새로운_디바이스_토큰을_저장한다() {
        // Given
        User user = fixtureMonkey.giveMeOne(User.class);
        String token = "new-device-token";
        DeviceTokenPersistRequest request = new DeviceTokenPersistRequest(token);
        DeviceToken newDeviceToken = new DeviceToken(token, user);

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(deviceTokenRepository.findByUserId(user.getId())).thenReturn(Optional.empty());
        when(deviceTokenRepository.save(any(DeviceToken.class))).thenReturn(newDeviceToken);

        // When
        DeviceTokenResponse response = deviceTokenService.persist(request, user.getId());

        // Then
        assertNotNull(response);
        assertEquals(token, response.token());
        verify(deviceTokenRepository).save(any(DeviceToken.class));
    }

    @Test
    void 기존_디바이스_토큰을_업데이트한다() {
        // Given
        User user = fixtureMonkey.giveMeOne(User.class);
        String oldToken = "old-device-token";
        String newToken = "new-device-token";
        DeviceTokenPersistRequest request = new DeviceTokenPersistRequest(newToken);
        DeviceToken existingDeviceToken = new DeviceToken(oldToken, user);

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(deviceTokenRepository.findByUserId(user.getId())).thenReturn(Optional.of(existingDeviceToken));

        // When
        DeviceTokenResponse response = deviceTokenService.persist(request, user.getId());

        // Then
        assertNotNull(response);
        assertEquals(newToken, existingDeviceToken.getToken()); // 실제 객체가 업데이트되었는지 확인
        verify(deviceTokenRepository, never()).save(any(DeviceToken.class)); // 새로운 저장이 발생하지 않았는지 검증
    }

    @Test
    void 인증되지_않은_사용자가_디바이스_토큰_저장_요청하면_예외를_발생시킨다() {
        // Given
        String token = "device-token";
        Long invalidUserId = 1L;
        DeviceTokenPersistRequest request = new DeviceTokenPersistRequest(token);

        when(userRepository.findById(invalidUserId)).thenReturn(Optional.empty());

        // When & Then
        CustomException exception = assertThrows(CustomException.class, () -> deviceTokenService.persist(request, invalidUserId));
        assertEquals(ErrorCode.AUTHORIZATION_FAILED, exception.getErrorCode());
    }
}
