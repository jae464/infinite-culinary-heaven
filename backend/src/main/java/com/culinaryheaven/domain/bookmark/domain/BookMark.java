package com.culinaryheaven.domain.bookmark.domain;

import com.culinaryheaven.common.BaseTimeEntity;
import com.culinaryheaven.domain.recipe.domain.Recipe;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class BookMark extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "recipe_id", nullable = false, foreignKey = @ForeignKey(name = "fk_bookmark_recipe"))
    private Recipe recipe;

    @Column(nullable = false)
    private Long userId;

    @Builder
    public BookMark(Recipe recipe, Long userId) {
        this.recipe = recipe;
        this.userId = userId;
    }

}
