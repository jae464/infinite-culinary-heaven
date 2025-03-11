package com.culinaryheaven.domain.user.controller;

import com.culinaryheaven.domain.user.dto.response.FollowResponse;
import com.culinaryheaven.domain.user.dto.response.FollowsResponse;
import com.culinaryheaven.domain.user.service.FollowService;
import com.culinaryheaven.global.annotation.Authenticated;
import com.culinaryheaven.global.security.PrincipalUserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/follows")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @PostMapping("/{userId}")
    public ResponseEntity<FollowResponse> followUser(
            @Authenticated PrincipalUserInfo userInfo,
            @PathVariable Long userId
    ) {
        FollowResponse followResponse = followService.followUser(userId, userInfo.userId());
        return ResponseEntity.ok().body(followResponse);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<FollowsResponse> getUserFollows(
            @PathVariable Long userId
    ) {
        FollowsResponse followsResponse = followService.getFollows(userId);
        return ResponseEntity.ok().body(followsResponse);
    }


    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUserFollows(
            @Authenticated PrincipalUserInfo userInfo,
            @PathVariable Long userId
    ) {
        followService.unfollowUser(userId, userInfo.userId());
        return ResponseEntity.noContent().build();
    }

}
