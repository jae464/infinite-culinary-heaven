package com.culinaryheaven.domain.contest.repository;

import com.culinaryheaven.domain.contest.domain.Contest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContestRepository extends JpaRepository<Contest, Long> {
    Page<Contest> findAll(Pageable pageable);
}
