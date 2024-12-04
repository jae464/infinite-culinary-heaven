package com.culinaryheaven.domain.user.controller;

import com.culinaryheaven.domain.user.dto.request.UserUpdateRequest;
import com.culinaryheaven.domain.user.dto.response.UserInfoResponse;
import com.culinaryheaven.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserInfoResponse> getMyInfo() {
        UserInfoResponse userInfoResponse = userService.getMyInfo();
        return ResponseEntity.ok().body(userInfoResponse);
    }

    @PatchMapping("/me")
    public ResponseEntity<UserInfoResponse> updateMyInfo(
            @RequestPart(required = false) @Valid UserUpdateRequest userUpdateRequest,
            @RequestPart(required = false) MultipartFile profileImage
    ) {
        UserInfoResponse userInfoResponse = userService.updateMyInfo(userUpdateRequest, profileImage);
        return ResponseEntity.ok().body(userInfoResponse);
    }

}
