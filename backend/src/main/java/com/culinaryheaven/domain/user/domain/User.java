package com.culinaryheaven.domain.user.domain;


import com.culinaryheaven.common.BaseTimeEntity;
import com.culinaryheaven.domain.auth.domain.OAuth2Type;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "users")
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column
    private String profileImageUrl;

    @Column
    private String oauthId;

    @Enumerated(EnumType.STRING)
    @Column
    private OAuth2Type oauthType;

    @Builder
    public User(String username, String oauthId, OAuth2Type oauthType) {
        this.username = username;
        this.oauthId = oauthId;
        this.oauthType = oauthType;
    }

    public void updateUsername(String username) {
        this.username = username;
    }

    public void updateProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

}
