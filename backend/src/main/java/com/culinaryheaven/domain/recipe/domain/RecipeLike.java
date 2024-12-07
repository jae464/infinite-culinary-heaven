package com.culinaryheaven.domain.recipe.domain;

import com.culinaryheaven.common.BaseTimeEntity;
import com.culinaryheaven.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RecipeLike extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "recipe_id", nullable = false, foreignKey = @ForeignKey(name = "fk_like_recipe"))
    private Recipe recipe;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_like_user"))
    private User user;

    @Builder
    public RecipeLike(Recipe recipe, User user) {
        this.recipe = recipe;
        this.user = user;
    }

}
