package com.culinaryheaven.domain.bookmark.dto.response;

import com.culinaryheaven.domain.bookmark.domain.BookMark;
import com.culinaryheaven.domain.recipe.dto.response.RecipeResponse;
import com.culinaryheaven.domain.recipe.dto.response.RecipesResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public record BookMarksResponse(
        List<BookMarkResponse> bookMarks
) {

    public static BookMarksResponse of(Page<BookMark> bookMarks) {
        List<BookMarkResponse> bookMarksResponse = bookMarks.getContent()
                .stream()
                .map(BookMarkResponse::of)
                .toList();

        return new BookMarksResponse(bookMarksResponse);

    }
}
