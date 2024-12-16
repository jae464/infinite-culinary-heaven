package com.culinaryheaven.domain.device.repository;

import com.culinaryheaven.domain.device.domain.DeviceToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeviceTokenRepository extends JpaRepository<DeviceToken, Long> {
    Optional<DeviceToken> findByToken(String token);
    Optional<DeviceToken> findByUserId(Long userId);
}
