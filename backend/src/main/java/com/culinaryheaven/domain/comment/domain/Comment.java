package com.culinaryheaven.domain.comment.domain;

import com.culinaryheaven.common.BaseTimeEntity;
import com.culinaryheaven.domain.recipe.domain.Recipe;
import com.culinaryheaven.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Comment extends BaseTimeEntity {

    @GeneratedValue
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column
    private String content;

    @Builder
    public Comment(Recipe recipe, User user, String content) {
        this.recipe = recipe;
        this.user = user;
        this.content = content;
    }

}
