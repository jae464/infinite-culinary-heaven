package com.culinaryheaven.domain.bookmark.service;

import com.culinaryheaven.domain.bookmark.domain.BookMark;
import com.culinaryheaven.domain.bookmark.dto.request.BookMarkCreateRequest;
import com.culinaryheaven.domain.bookmark.dto.response.BookMarkResponse;
import com.culinaryheaven.domain.bookmark.dto.response.BookMarksResponse;
import com.culinaryheaven.domain.bookmark.repository.BookMarkRepository;
import com.culinaryheaven.domain.recipe.domain.Recipe;
import com.culinaryheaven.domain.recipe.repository.RecipeRepository;
import com.culinaryheaven.domain.user.domain.User;
import com.culinaryheaven.domain.user.repository.UserRepository;
import com.culinaryheaven.global.exception.CustomException;
import com.culinaryheaven.global.exception.ErrorCode;
import com.culinaryheaven.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookMarkService {

    private final BookMarkRepository bookMarkRepository;
    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;
    private final SecurityUtil securityUtil;

    public BookMarkResponse addBookMark(BookMarkCreateRequest request) {

        User user = userRepository.findByOauthId(
                securityUtil.getUserOAuth2Id()).orElseThrow(
                () -> new CustomException(ErrorCode.AUTHORIZATION_FAILED)
        );

        validateBookMark(request.recipeId(), user.getId());

        Recipe recipe = recipeRepository.findById(request.recipeId()).orElseThrow(() -> new CustomException(ErrorCode.RECIPE_NOT_FOUND));

        BookMark bookMark = BookMark.builder()
                .recipe(recipe)
                .userId(user.getId())
                .build();

        BookMark savedBookMark = bookMarkRepository.save(bookMark);

        return BookMarkResponse.of(savedBookMark);

    }

    public BookMarksResponse getAllBookMarks(Pageable pageable) {

        User user = userRepository.findByOauthId(
                securityUtil.getUserOAuth2Id()).orElseThrow(
                () -> new CustomException(ErrorCode.AUTHORIZATION_FAILED)
        );

        Page<BookMark> bookMarks = bookMarkRepository.findAllByUserId(pageable, user.getId());

        return BookMarksResponse.of(bookMarks);

    }

    public void deleteBookMarkByRecipeId(Long recipeId) {
        User user = userRepository.findByOauthId(securityUtil.getUserOAuth2Id()).orElseThrow(
                () -> new CustomException(ErrorCode.AUTHORIZATION_FAILED)
        );

        BookMark bookMark = bookMarkRepository.findByRecipeIdAndUserId(recipeId, user.getId()).orElseThrow(
                () -> new CustomException(ErrorCode.BOOKMARK_NOT_FOUND)
        );

        validateBookMarkOwner(bookMark);

        bookMarkRepository.delete(bookMark);
    }

    private void validateBookMark(Long recipeId, Long userId) {
        boolean exists = bookMarkRepository.existsByRecipeIdAndUserId(recipeId, userId);

        if (exists) {
            throw new CustomException(ErrorCode.ALREADY_EXISTS_BOOKMARK);
        }
    }

    private void validateBookMarkOwner(BookMark bookMark) {
        System.out.println("validateBookMarkOwner");
        User user = userRepository.findByOauthId(
                securityUtil.getUserOAuth2Id()).orElseThrow(
                        () -> new CustomException(ErrorCode.AUTHORIZATION_FAILED)
        );

        if (!bookMark.getUserId().equals(user.getId())) {
            throw new CustomException(ErrorCode.FORBIDDEN);
        }
    }

}
