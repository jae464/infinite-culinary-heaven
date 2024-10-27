package com.jae464.data.repository

import com.jae464.domain.model.Ingredient
import com.jae464.domain.model.Recipe
import com.jae464.domain.model.RecipePreview
import com.jae464.domain.repository.RecipeRepository
import javax.inject.Inject

class FakeRecipeRepository @Inject constructor() : RecipeRepository {
    override suspend fun getRecipePreviews(): List<RecipePreview> {
        val mockData = listOf(
            RecipePreview(
                id = 1L,
                title = "감자 베이컨 말이",
                imageUrl = "https://i.ibb.co/TbhqvXP/potate-3.jpg",
                description = "감자와 베이컨의 조합 ㄷㄷ",
                score = 5.0f,
                author = "나폴리맛피아"
            ),
            RecipePreview(
                id = 2L,
                title = "치즈 감자구이",
                imageUrl = "https://i.ibb.co/8Ys5Tc2/potato-1.jpg",
                description = "저는 캄자인간 입니돠~",
                score = 5.0f,
                author = "이균"
            ),
            RecipePreview(
                id = 3L,
                title = "감자 버터구이",
                imageUrl = "https://recipe1.ezmember.co.kr/cache/recipe/2017/08/30/4c641aaf9fe89859966922d914a3c8f51.jpg",
                description = "요만큼에서 이만큼 느껴지는 버터향을 느껴보세요",
                score = 5.0f,
                author = "트리플스타"
            ),
            RecipePreview(
                id = 4L,
                title = "휴게소 알감자",
                imageUrl = "https://recipe1.ezmember.co.kr/cache/recipe/2022/10/13/d0e19dee8c75fb7856df44c3b95655411.jpg",
                description = "휴게소 알감자는 진리지",
                score = 5.0f,
                author = "요리하는돌아이"
            )
        )
        val recipePreviews = mockData + mockData
        return recipePreviews
    }

    override suspend fun getRecipeById(id: Long): Recipe {
        return Recipe(
            id = 1L,
            title = "감자 베이컨 말이",
            imageUrl = "https://i.ibb.co/TbhqvXP/potate-3.jpg",
            description = "감자와 베이컨으로 만들 수 있는 감자 베이컨 말이 입니다.\n" +
                    "누구나 쉽게 간편하게 만들 수 있어요.",
            score = 5.0f,
            author = "나폴리맛피아",
            ingredients = listOf(
                Ingredient("감자", "2개"),
                Ingredient("베이컨", "6개"),
                Ingredient("올리브유", "1큰술"),
                Ingredient("소금", "약간"),
                Ingredient("후추", "약간"),
                Ingredient("파슬리", "약간")
            ),
            steps = listOf(
                "감자를 얇게 슬라이스한 후 끓는 물에 3분 정도 삶아줍니다.",
                "베이컨을 감자에 말아줍니다.",
                "팬에 올리브유를 두르고 중불에서 베이컨을 감싼 감자를 골고루 굴려가며 구워줍니다.",
                "소금과 후추로 간을 하고, 파슬리를 뿌려 마무리합니다."
            ),
            cookTime = "15분",
            servingSize = "2인분",
            videoUrl = "https://www.youtube.com/watch?v=example"
        )

    }
}