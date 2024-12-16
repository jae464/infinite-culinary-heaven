package com.culinaryheaven.global.controller;

import com.culinaryheaven.global.notification.FcmNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class HealthController {

    private final FcmNotificationService fcmNotificationService;

    @GetMapping("/health")
    public String health() {
        log.error("slack 알림 test");
        return "OK2";
    }

    @PostMapping("/notification")
    public String notificationTest() {
        fcmNotificationService.sendNotification("haha", "haha", 1L);
        return "OK1";
    }
}
