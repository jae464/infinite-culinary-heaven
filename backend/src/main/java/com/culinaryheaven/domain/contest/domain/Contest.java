package com.culinaryheaven.domain.contest.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Contest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

    @ManyToOne
    @JoinColumn(name = "topic_ingredient_id", nullable = false)
    private TopicIngredient topicIngredient;

    @Builder
    public Contest(String description, LocalDateTime startDate, LocalDateTime endDate, TopicIngredient topicIngredient) {
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.topicIngredient = topicIngredient;
    }

}
