package com.culinaryheaven.domain.comment.repository;

import com.culinaryheaven.domain.comment.domain.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByRecipeId(Sort sort, Long recipeId);
}
