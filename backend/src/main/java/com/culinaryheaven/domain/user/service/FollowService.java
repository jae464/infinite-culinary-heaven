package com.culinaryheaven.domain.user.service;

import com.culinaryheaven.domain.user.domain.Follow;
import com.culinaryheaven.domain.user.domain.User;
import com.culinaryheaven.domain.user.dto.response.FollowResponse;
import com.culinaryheaven.domain.user.repository.FollowRepository;
import com.culinaryheaven.domain.user.repository.UserRepository;
import com.culinaryheaven.global.exception.CustomException;
import com.culinaryheaven.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    @Transactional
    public FollowResponse followUser(Long userId, String oauth2Id) {
        User currentUser = userRepository
                .findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUNT));

        User targetUser = userRepository.findById(userId).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUNT));
        Follow follow = Follow.builder()
                .source(currentUser)
                .target(targetUser)
                .build();

        Follow currentFollow = followRepository.save(follow);

        return FollowResponse.of(currentFollow.getTarget().getId(), currentFollow.getTarget().getUsername());

    }

}
