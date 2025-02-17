package com.culinaryheaven.domain.user.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "source_user_id")
    private User source;

    @ManyToOne
    @JoinColumn(name = "target_user_id")
    private User target;

    @Builder
    public Follow(User source, User target) {
        this.source = source;
        this.target = target;
    }
}
