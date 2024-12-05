package com.culinaryheaven.domain.bookmark.repository;

import com.culinaryheaven.domain.bookmark.domain.BookMark;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookMarkRepository extends JpaRepository<BookMark, Long> {
    boolean existsByRecipeIdAndUserId(Long recipeId, Long userId);
    Page<BookMark> findAllByUserId(Pageable pageable, Long userId);
    Optional<BookMark> findByRecipeIdAndUserId(Long recipeId, Long userId);
}
