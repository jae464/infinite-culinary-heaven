package com.culinaryheaven.domain.recipe.domain;

import com.culinaryheaven.common.BaseTimeEntity;
import com.culinaryheaven.domain.bookmark.domain.BookMark;
import com.culinaryheaven.domain.comment.domain.Comment;
import com.culinaryheaven.domain.contest.domain.Contest;
import com.culinaryheaven.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
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
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "contest_id", nullable = false)
    private Contest contest;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookMark> bookmarks = new ArrayList<>();

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecipeLike> likes = new ArrayList<>();

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ingredient> ingredients = new ArrayList<>();

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Step> steps = new ArrayList<>();

    @Builder
    public Recipe(String title,
                  String description,
                  String thumbnailImage,
                  User user,
                  Contest contest
    ) {
        this.title = title;
        this.description = description;
        this.thumbnailImage = thumbnailImage;
        this.user = user;
        this.contest = contest;
    }

    public int getBookMarkCount() {
        return bookmarks.size();
    }

    public int getLikeCount() {
        return likes.size();
    }

    public int getCommentCount() { return comments.size(); }

}
