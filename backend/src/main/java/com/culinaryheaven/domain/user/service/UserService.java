package com.culinaryheaven.domain.user.service;

import com.culinaryheaven.domain.image.ImageStorageClient;
import com.culinaryheaven.domain.user.domain.User;
import com.culinaryheaven.domain.user.dto.request.UserUpdateRequest;
import com.culinaryheaven.domain.user.dto.response.UserInfoResponse;
import com.culinaryheaven.domain.user.repository.UserRepository;
import com.culinaryheaven.global.exception.CustomException;
import com.culinaryheaven.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ImageStorageClient imageStorageClient;

    @Transactional(readOnly = true)
    public UserInfoResponse getMyInfo(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.AUTHORIZATION_FAILED));

        return UserInfoResponse.of(user);

    }

    @Transactional(readOnly = true)
    public UserInfoResponse getUserInfo(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUNT));

        return UserInfoResponse.of(user);
    }

    @Transactional
    public UserInfoResponse updateMyInfo(
            UserUpdateRequest request,
            MultipartFile profileImage,
            Long userId
    ) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.AUTHORIZATION_FAILED));

        if (request.userNickname() != null) {
            user.updateUsername(request.userNickname());
        }

        if (profileImage != null) {
            String imageUrl = imageStorageClient.uploadImage(profileImage);
            user.updateProfileImageUrl(imageUrl);
        }

        return UserInfoResponse.of(user);
    }

}
