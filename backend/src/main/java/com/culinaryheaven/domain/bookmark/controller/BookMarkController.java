package com.culinaryheaven.domain.bookmark.controller;

import com.culinaryheaven.domain.bookmark.dto.response.BookMarkResponse;
import com.culinaryheaven.domain.bookmark.dto.response.BookMarksResponse;
import com.culinaryheaven.domain.bookmark.service.BookMarkService;
import com.culinaryheaven.global.annotation.Authenticated;
import com.culinaryheaven.global.security.PrincipalUserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookmarks")
@RequiredArgsConstructor
public class BookMarkController {

    private final BookMarkService bookMarkService;

    @PostMapping("/{recipeId}")
    public ResponseEntity<BookMarkResponse> create(
            @PathVariable Long recipeId
    ) {
        BookMarkResponse bookMarkResponse = bookMarkService.addBookMark(recipeId);
        return ResponseEntity.ok().body(bookMarkResponse);
    }

    @GetMapping("/me")
    public ResponseEntity<BookMarksResponse> getMyBookMarks(
            @Authenticated PrincipalUserInfo principalUserInfo,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        BookMarksResponse bookMarksResponse = bookMarkService.getAllBookMarks(pageable, principalUserInfo.oauth2Id());
        return ResponseEntity.ok().body(bookMarksResponse);
    }

    @DeleteMapping("/{recipeId}")
    public ResponseEntity<Void> delete(
            @Authenticated PrincipalUserInfo principalUserInfo,
            @PathVariable Long recipeId
    ) {
        bookMarkService.deleteBookMarkByRecipeId(recipeId, principalUserInfo.oauth2Id());
        return ResponseEntity.noContent().build();
    }
}
