package com.jae464.data.repository

import com.jae464.domain.model.RecipePreview
import com.jae464.domain.repository.BookMarkRepository
import javax.inject.Inject

class FakeBookMarkRepository @Inject constructor() : BookMarkRepository {
    override suspend fun getBookMarkedRecipes(): Result<List<RecipePreview>> {
        return Result.success(
            listOf(
                RecipePreview(
                    id = 1L,
                    title = "감자 베이컨 말이",
                    imageUrl = "https://recipe1.ezmember.co.kr/cache/recipe/2019/07/12/1c5436c0bab2b8385ee134cbe60243c71.jpg",
                    description = "감자와 베이컨으로 간단하게 만들수 있는 요리입니다.",
                    score = 5.0f,
                    author = "나폴리맛피아"
                ),
            )
        )
    }

    override suspend fun addBookMark(recipeId: Long): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteBookMark(recipeId: Long): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun isBookMarked(recipeId: Long): Result<Boolean> {
        TODO("Not yet implemented")
    }
}