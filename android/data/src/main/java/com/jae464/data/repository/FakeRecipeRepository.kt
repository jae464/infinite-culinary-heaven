package com.jae464.data.repository

import com.jae464.domain.model.RecipePreview
import com.jae464.domain.repository.RecipeRepository
import javax.inject.Inject

class FakeRecipeRepository @Inject constructor() : RecipeRepository {
    override suspend fun getRecipePreviews(): List<RecipePreview> {
        val mockData = listOf(
            RecipePreview(
                id = 1L,
                title = "감자치즈",
                imageUrl = "https://i.ibb.co/TbhqvXP/potate-3.jpg",
                description = "따라쟁이로 만들었는데 맛있어요~~^^ 최고예요~!!!",
                score = 5.0f,
                author = "해연"
            ),
            RecipePreview(
                id = 2L,
                title = "감자칩",
                imageUrl = "https://i.ibb.co/8Ys5Tc2/potato-1.jpg",
                description = "식당에서 나온 밑반찬으로 먹어보고 맛있어서 바로 레시피 보고 만들었어요!",
                score = 5.0f,
                author = "초파맘"
            ),
            RecipePreview(
                id = 3L,
                title = "꿀맛감자",
                imageUrl = "https://i.ibb.co/f0NDs68/potato-2.jpg",
                description = "처음 만들어 봤는데 너무 맛있어요!! 가족들이랑 너무 맛있게 잘 먹었어요",
                score = 5.0f,
                author = "민지"
            ),
            RecipePreview(
                id = 4L,
                title = "감자짜글이",
                imageUrl = "https://i.ibb.co/TbhqvXP/potate-3.jpg",
                description = "간단한데 맛있게 잘 만들어 먹었습니다! 감자 돼지고기 버섯까지 넣어서 야무지게 먹었어요",
                score = 5.0f,
                author = "지니잉"
            )
        )
        val recipePreviews = mockData + mockData
        return recipePreviews
    }
}