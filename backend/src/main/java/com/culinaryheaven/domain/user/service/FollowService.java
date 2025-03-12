package com.culinaryheaven.domain.user.service;

import com.culinaryheaven.domain.user.domain.Follow;
import com.culinaryheaven.domain.user.domain.User;
import com.culinaryheaven.domain.user.dto.response.FollowResponse;
import com.culinaryheaven.domain.user.dto.response.FollowStatus;
import com.culinaryheaven.domain.user.dto.response.FollowStatusResponse;
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

        if (currentUser.getId().equals(targetUser.getId())) {
            throw new CustomException(ErrorCode.SELF_FOLLOW_NOT_ALLOWED);
        }

        if (isFollowing(currentUser, targetUser)) {
            throw new CustomException(ErrorCode.ALREADY_EXISTS_FOLLOW);
        }

        Follow follow = Follow.builder()
                .source(currentUser)
                .target(targetUser)
                .build();

        Follow savedFollow = followRepository.save(follow);

        return FollowResponse.of(savedFollow);
    }

    @Transactional(readOnly = true)
    public FollowStatusResponse getFollowStatus(Long targetUserId, Long userId) {
        User sourceUser = userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUNT)
        );
        User targetUser = userRepository.findById(targetUserId).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUNT)
        );

        if (isFollowing(sourceUser, targetUser)) {
          return FollowStatusResponse.of(FollowStatus.FOLLOWING);
        } else {
            return FollowStatusResponse.of(FollowStatus.NOT_FOLLOWING);
        }
    }

    private boolean isFollowing(User sourceUser, User targetUser) {
        return followRepository.existsBySourceAndTarget(sourceUser, targetUser);
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
