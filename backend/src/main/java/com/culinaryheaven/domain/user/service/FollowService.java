package com.culinaryheaven.domain.user.service;

import com.culinaryheaven.domain.user.domain.Follow;
import com.culinaryheaven.domain.user.domain.User;
import com.culinaryheaven.domain.user.dto.response.FollowResponse;
import com.culinaryheaven.domain.user.dto.response.FollowsResponse;
import com.culinaryheaven.domain.user.repository.FollowRepository;
import com.culinaryheaven.domain.user.repository.UserRepository;
import com.culinaryheaven.global.exception.CustomException;
import com.culinaryheaven.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    @Transactional
    public FollowResponse followUser(Long targetUserId, Long userId) {
        User currentUser = userRepository
                .findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUNT));

        User targetUser = userRepository.findById(targetUserId).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUNT));

        Follow follow = Follow.builder()
                .source(currentUser)
                .target(targetUser)
                .build();

        Follow savedFollow = followRepository.save(follow);

        return FollowResponse.of(savedFollow);
    }

    @Transactional
    public void unfollowUser(Long targetUserId, Long userId) {
        User sourceUser = userRepository.findById(userId).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUNT));
        User targetUser = userRepository.findById(targetUserId).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUNT));
        Follow follow = followRepository.findBySourceAndTarget(sourceUser, targetUser).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUNT));
        followRepository.delete(follow);
    }

    @Transactional(readOnly = true)
    public FollowsResponse getFollows(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUNT));
        List<Follow> follows = followRepository.findBySource(user);
        return FollowsResponse.of(follows);
    }


}
