package com.jae464.data.repository

import com.jae464.domain.model.Ingredient
import com.jae464.domain.model.Recipe
import com.jae464.domain.model.RecipePreview
import com.jae464.domain.model.Step
import com.jae464.domain.repository.RecipeRepository
import javax.inject.Inject

class FakeRecipeRepository @Inject constructor() : RecipeRepository {
    override suspend fun getRecipePreviews(): List<RecipePreview> {
        val mockData = listOf(
            RecipePreview(
                id = 1L,
                title = "감자 베이컨 말이",
                imageUrl = "https://recipe1.ezmember.co.kr/cache/recipe/2019/07/12/1c5436c0bab2b8385ee134cbe60243c71.jpg",
                description = "감자와 베이컨으로 간단하게 만들수 있는 요리입니다.",
                score = 5.0f,
                author = "나폴리맛피아"
            ),
            RecipePreview(
                id = 2L,
                title = "치즈 감자구이",
                imageUrl = "https://i.ibb.co/8Ys5Tc2/potato-1.jpg",
                description = "고소한 치즈와 부드러운 감자가 어우러진 최고의 간식입니다.",
                score = 5.0f,
                author = "에드워드 리"
            ),
            RecipePreview(
                id = 3L,
                title = "감자 버터구이",
                imageUrl = "https://recipe1.ezmember.co.kr/cache/recipe/2017/08/30/4c641aaf9fe89859966922d914a3c8f51.jpg",
                description = "버터로 노릇하게 구운 감자가 입안을 사로잡습니다.",
                score = 5.0f,
                author = "트리플스타"
            ),
            RecipePreview(
                id = 4L,
                title = "휴게소 알감자",
                imageUrl = "https://recipe1.ezmember.co.kr/cache/recipe/2022/10/13/d0e19dee8c75fb7856df44c3b95655411.jpg",
                description = "간단하면서도 깊은 맛을 자랑하는 휴게소 스타일의 알감자입니다.",
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
            imageUrl = "https://recipe1.ezmember.co.kr/cache/recipe/2019/07/12/1c5436c0bab2b8385ee134cbe60243c71.jpg",
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
                Step(
                    step = 1,
                    description = "감자를 얇게 슬라이스한 후 끓는 물에 3분 정도 삶아줍니다.",
                    imageUrl = "https://i.ytimg.com/vi/oBFUGtO40YI/maxresdefault.jpg"
                ),
                Step(
                    step = 2,
                    description = "베이컨을 감자에 말아줍니다.",
                    imageUrl = "https://recipe1.ezmember.co.kr/cache/recipe/2021/10/28/699c41df4bd92fc73610010faacae3461.jpg"
                ),
                Step(
                    step = 3,
                    description = "팬에 올리브유를 두르고 중불에서 베이컨을 감싼 감자를 골고루 굴려가며 구워줍니다.",
                    imageUrl = "https://mblogthumb-phinf.pstatic.net/20160626_126/sjlove321_1466914687299l4SG4_JPEG/20160626_123930111.jpg?type=w420"
                ),
                Step(
                    step = 4,
                    description = "소금과 후추로 간을 하고, 파슬리를 뿌려 마무리합니다.",
                    imageUrl = "https://mblogthumb-phinf.pstatic.net/20140814_67/99jinga_14079837901420Jjea_JPEG/IMG_6699.JPG?type=w420"
                )
            ),
        )

    }
}