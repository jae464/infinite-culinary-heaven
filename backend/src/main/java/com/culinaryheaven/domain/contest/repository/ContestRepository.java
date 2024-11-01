package com.culinaryheaven.domain.contest.repository;

import com.culinaryheaven.domain.contest.domain.Contest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContestRepository extends JpaRepository<Contest, Long> {
}
