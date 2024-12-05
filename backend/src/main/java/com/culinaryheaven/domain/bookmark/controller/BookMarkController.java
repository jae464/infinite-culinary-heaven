package com.culinaryheaven.domain.bookmark.controller;

import com.culinaryheaven.domain.bookmark.dto.request.BookMarkCreateRequest;
import com.culinaryheaven.domain.bookmark.dto.response.BookMarkResponse;
import com.culinaryheaven.domain.bookmark.dto.response.BookMarksResponse;
import com.culinaryheaven.domain.bookmark.service.BookMarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookmarks")
@RequiredArgsConstructor
public class BookMarkController {

    private final BookMarkService bookMarkService;

    @PostMapping
    public ResponseEntity<BookMarkResponse> create(
            @RequestBody BookMarkCreateRequest bookMarkCreateRequest
    ) {
        BookMarkResponse bookMarkResponse = bookMarkService.addBookMark(bookMarkCreateRequest);
        return ResponseEntity.ok().body(bookMarkResponse);
    }

    @GetMapping
    public ResponseEntity<BookMarksResponse> getAllBookMarks(
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        BookMarksResponse bookMarksResponse = bookMarkService.getAllBookMarks(pageable);
        return ResponseEntity.ok().body(bookMarksResponse);
    }

    @DeleteMapping("/{recipeId}")
    public ResponseEntity<BookMarkResponse> delete(
            @PathVariable Long recipeId
    ) {
        bookMarkService.deleteBookMarkByRecipeId(recipeId);
        return ResponseEntity.noContent().build();
    }
}
