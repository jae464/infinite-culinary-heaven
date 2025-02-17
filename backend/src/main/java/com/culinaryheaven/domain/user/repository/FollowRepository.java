package com.culinaryheaven.domain.user.repository;

import com.culinaryheaven.domain.user.domain.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Long> {
}
