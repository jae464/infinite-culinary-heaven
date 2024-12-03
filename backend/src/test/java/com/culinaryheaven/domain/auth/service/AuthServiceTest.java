package com.culinaryheaven.domain.auth.service;

import com.culinaryheaven.domain.auth.domain.TokenType;
import com.culinaryheaven.domain.auth.dto.request.AdminLoginRequest;
import com.culinaryheaven.domain.auth.dto.request.ReissueRequest;
import com.culinaryheaven.domain.auth.dto.response.LoginResponse;
import com.culinaryheaven.domain.auth.dto.response.ReissueResponse;
import com.culinaryheaven.domain.auth.infrastructure.JwtTokenProvider;
import com.culinaryheaven.domain.auth.infrastructure.dto.response.UserInfoResponse;
import com.culinaryheaven.domain.auth.infrastructure.kakao.KakaoOAuth2Client;
import com.culinaryheaven.domain.user.domain.User;
import com.culinaryheaven.domain.user.repository.UserRepository;
import com.culinaryheaven.global.exception.CustomException;
import com.culinaryheaven.global.exception.ErrorCode;
import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.FieldReflectionArbitraryIntrospector;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private KakaoOAuth2Client oAuth2Client;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private AuthService authService;

    private FixtureMonkey fixtureMonkey;

    @BeforeEach
    void setUp() {
        fixtureMonkey = FixtureMonkey.builder()
                .objectIntrospector(FieldReflectionArbitraryIntrospector.INSTANCE)
                .build();
    }

    @Test
    void 카카오_로그인_후_엑세스_토큰과_리프레시_토큰을_리턴한다() {
        // Given
        String oauth2Type = "KAKAO";
        String oauth2AccessToken = "validAccessToken";
        UserInfoResponse userInfoResponse = new UserInfoResponse(12345L);
        String accessToken = "accessToken";
        String refreshToken = "refreshToken";
        User user = fixtureMonkey.giveMeOne(User.class);

        when(oAuth2Client.getUserInfo(oauth2AccessToken)).thenReturn(userInfoResponse);
        when(jwtTokenProvider.provideToken(12345L, TokenType.ACCESS, "ROLE_USER")).thenReturn(accessToken);
        when(jwtTokenProvider.provideToken(12345L, TokenType.REFRESH, "ROLE_USER")).thenReturn(refreshToken);
        when(userRepository.findByOauthId("12345")).thenReturn(Optional.of(user));

        // When
        LoginResponse response = authService.login(oauth2Type, oauth2AccessToken);

        // Then
        assertNotNull(response);
        assertEquals(accessToken, response.accessToken());
        assertEquals(refreshToken, response.refreshToken());
    }

    @Test
    void 관리자_로그인_후_엑세스_토큰과_리프레시_토큰을_리턴한다() {
        // Given
        AdminLoginRequest adminLoginRequest = new AdminLoginRequest("admin", "admin");
        String accessToken = "adminAccessToken";
        String refreshToken = "adminRefreshToken";

        when(jwtTokenProvider.provideToken(1L, TokenType.ACCESS, "ROLE_ADMIN")).thenReturn(accessToken);
        when(jwtTokenProvider.provideToken(1L, TokenType.REFRESH, "ROLE_ADMIN")).thenReturn(refreshToken);

        // When
        LoginResponse response = authService.loginAsAdmin(adminLoginRequest);

        // Then
        assertNotNull(response);
        assertEquals(accessToken, response.accessToken());
        assertEquals(refreshToken, response.refreshToken());
    }

    @Test
    void 관리자_로그인_실패시_예외() {
        // Given
        AdminLoginRequest adminLoginRequest = new AdminLoginRequest("wrong", "credentials");

        // When & Then
        CustomException exception = assertThrows(CustomException.class, () -> authService.loginAsAdmin(adminLoginRequest));
        assertEquals(ErrorCode.AUTHORIZATION_FAILED, exception.getErrorCode());
    }

    @Test
    void 엑세스_토큰_재발급_오청시_새로운_토큰을_리턴한다() {
        User user = fixtureMonkey.giveMeOne(User.class);
        // Given
        String refreshToken = "validRefreshToken";
        String newAccessToken = "newAccessToken";
        String newRefreshToken = "newRefreshToken";
        Claims claims = mock(Claims.class);
        ReissueRequest request = new ReissueRequest(refreshToken);

        when(jwtTokenProvider.validateRefreshToken(refreshToken)).thenReturn(true);
        when(jwtTokenProvider.getClaimsFromToken(refreshToken, TokenType.REFRESH)).thenReturn(claims);
        when(claims.getSubject()).thenReturn("12345");
        when(claims.get("memberRole", String.class)).thenReturn("ROLE_USER");
        when(userRepository.findByOauthId("12345")).thenReturn(Optional.of(user));
        when(jwtTokenProvider.provideToken(12345L, TokenType.ACCESS, "ROLE_USER")).thenReturn(newAccessToken);
        when(jwtTokenProvider.provideToken(12345L, TokenType.REFRESH, "ROLE_USER")).thenReturn(newRefreshToken);

        // When
        ReissueResponse response = authService.reissue(request);

        // Then
        assertNotNull(response);
        assertEquals(newAccessToken, response.accessToken());
        assertEquals(newRefreshToken, response.refreshToken());
    }

    @Test
    void 유효하지_않은_리프레스_토큰_예외() {
        // Given
        String refreshToken = "invalidRefreshToken";
        ReissueRequest request = new ReissueRequest(refreshToken);

        when(jwtTokenProvider.validateRefreshToken(refreshToken)).thenReturn(false);

        // When & Then
        CustomException exception = assertThrows(CustomException.class, () -> authService.reissue(request));
        assertEquals(ErrorCode.AUTHORIZATION_FAILED, exception.getErrorCode());
    }
}
