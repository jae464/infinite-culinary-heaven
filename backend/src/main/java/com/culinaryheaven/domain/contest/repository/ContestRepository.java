package com.culinaryheaven.domain.contest.repository;

import com.culinaryheaven.domain.contest.domain.Contest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ContestRepository extends JpaRepository<Contest, Long> {
    Page<Contest> findAll(Pageable pageable);
    @Query("SELECT c FROM Contest c WHERE c.startDate <= :currentDate AND c.endDate >= :currentDate")
    Optional<Contest> findContestsWithinCurrentDate(@Param("currentDate") LocalDateTime currentDate);
}
