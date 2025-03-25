package com.culinaryheaven.domain.user.service;

import com.culinaryheaven.domain.image.ImageStorageClient;
import com.culinaryheaven.domain.user.domain.User;
import com.culinaryheaven.domain.user.dto.request.UserUpdateRequest;
import com.culinaryheaven.domain.user.dto.response.UserInfoResponse;
import com.culinaryheaven.domain.user.repository.UserRepository;
import com.culinaryheaven.global.exception.CustomException;
import com.culinaryheaven.global.exception.ErrorCode;
import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.FieldReflectionArbitraryIntrospector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ImageStorageClient imageStorageClient;

    @InjectMocks
    private UserService userService;

    private FixtureMonkey fixtureMonkey;

    @BeforeEach
    void setUp() {
        fixtureMonkey = FixtureMonkey.builder()
                .objectIntrospector(FieldReflectionArbitraryIntrospector.INSTANCE)
                .defaultNotNull(true)
                .build();
    }

    @Test
    void 내_정보를_조회한다() {
        // Given
        User user = fixtureMonkey.giveMeOne(User.class);

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        // When
        UserInfoResponse response = userService.getMyInfo(user.getId());

        // Then
        assertNotNull(response);
        assertEquals(user.getId(), response.id());
        verify(userRepository).findById(user.getId());
    }

    @Test
    void 특정_사용자의_정보를_조회한다() {
        // Given
        Long userId = 1L;
        User user = fixtureMonkey.giveMeOne(User.class);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // When
        UserInfoResponse response = userService.getUserInfo(userId);

        // Then
        assertNotNull(response);
        assertEquals(user.getId(), response.id());
        verify(userRepository).findById(userId);
    }

    @Test
    void 존재하지_않는_사용자_조회시_예외를_발생시킨다() {
        // Given
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // When & Then
        CustomException exception = assertThrows(CustomException.class, () -> userService.getUserInfo(userId));
        assertEquals(ErrorCode.USER_NOT_FOUNT, exception.getErrorCode());
    }

    @Test
    void 내_정보를_수정한다() {
        // Given
        User user = fixtureMonkey.giveMeOne(User.class);
        UserUpdateRequest request = new UserUpdateRequest("new-nickname");
        MultipartFile profileImage = mock(MultipartFile.class);
        String imageUrl = "http://example.com/image.jpg";

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(imageStorageClient.uploadImage(profileImage)).thenReturn(imageUrl);

        // When
        UserInfoResponse response = userService.updateMyInfo(request, profileImage, user.getId());

        // Then
        assertNotNull(response);
        assertEquals(request.userNickname(), response.nickname());
        verify(userRepository).findById(user.getId());
        verify(imageStorageClient).uploadImage(profileImage);
    }

    @Test
    void 존재하지_않는_사용자_정보_수정시_예외를_발생시킨다() {
        // Given
        UserUpdateRequest request = new UserUpdateRequest("new-nickname");

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        CustomException exception = assertThrows(CustomException.class, () -> userService.updateMyInfo(request, null, 1L));
        assertEquals(ErrorCode.AUTHORIZATION_FAILED, exception.getErrorCode());
    }
}
