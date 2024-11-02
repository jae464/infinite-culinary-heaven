package com.culinaryheaven.domain.contest.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Contest {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "topic_ingredient_id", nullable = false)
    private TopicIngredient topicIngredient;

    @Builder
    public Contest(String description, TopicIngredient topicIngredient) {
        this.description = description;
        this.topicIngredient = topicIngredient;
    }

}