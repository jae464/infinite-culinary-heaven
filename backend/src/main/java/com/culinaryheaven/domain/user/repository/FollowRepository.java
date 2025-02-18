package com.culinaryheaven.domain.user.repository;

import com.culinaryheaven.domain.user.domain.Follow;
import com.culinaryheaven.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    List<Follow> findBySource(User source);
    Optional<Follow> findBySourceAndTarget(User source, User target);
}
