package com.culinaryheaven.domain.recipe.domain;

import com.culinaryheaven.common.BaseTimeEntity;
import com.culinaryheaven.domain.contest.domain.Contest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Recipe extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false, name = "thumbnail_image")
    private String thumbnailImage;

    @ManyToOne
    @JoinColumn(name = "contest_id", nullable = false)
    private Contest contest;

    @OneToMany(mappedBy = "recipe")
    private List<Ingredient> ingredients;

    @OneToMany(mappedBy = "recipe")
    private List<Step> steps;

    @Column(nullable = false, name = "competition_count")
    private int competitionCount = 0;

    @Column(nullable = false, name = "win_count")
    private int winCount = 0;

    @Builder
    public Recipe(String title,
                  String description,
                  String thumbnailImage,
                  Contest contest
    ) {
        this.title = title;
        this.description = description;
        this.thumbnailImage = thumbnailImage;
        this.contest = contest;
    }

}
