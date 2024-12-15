package com.culinaryheaven.global.controller;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class HealthController {

    @GetMapping("/health")
    public String health() {
        log.error("slack 알림 test");
        return "OK1";
    }
}
