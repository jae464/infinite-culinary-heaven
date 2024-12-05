package com.culinaryheaven.domain.user.service;

import com.culinaryheaven.domain.image.domain.ImageStorageClient;
import com.culinaryheaven.domain.user.domain.User;
import com.culinaryheaven.domain.user.dto.request.UserUpdateRequest;
import com.culinaryheaven.domain.user.dto.response.UserInfoResponse;
import com.culinaryheaven.domain.user.repository.UserRepository;
import com.culinaryheaven.global.exception.CustomException;
import com.culinaryheaven.global.exception.ErrorCode;
import com.culinaryheaven.global.util.SecurityUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ImageStorageClient imageStorageClient;
    private final SecurityUtil securityUtil;

    public UserInfoResponse getMyInfo() {
        User user = userRepository.findByOauthId(securityUtil.getUserOAuth2Id())
                .orElseThrow(() -> new CustomException(ErrorCode.AUTHORIZATION_FAILED));

        return UserInfoResponse.of(
                user.getId(), user.getUsername(), user.getProfileImageUrl()
        );

    }

    @Transactional
    public UserInfoResponse updateMyInfo(
            UserUpdateRequest request,
            MultipartFile profileImage
    ) {
        User user = userRepository.findByOauthId(securityUtil.getUserOAuth2Id())
                .orElseThrow(() -> new CustomException(ErrorCode.AUTHORIZATION_FAILED));

        if (request.userNickname() != null) {
            user.updateUsername(request.userNickname());
        }

        if (profileImage != null) {
            String imageUrl = imageStorageClient.uploadImage(profileImage);
            user.updateProfileImageUrl(imageUrl);
        }

        return UserInfoResponse.of(user.getId(), user.getUsername(), user.getProfileImageUrl());
    }

}
