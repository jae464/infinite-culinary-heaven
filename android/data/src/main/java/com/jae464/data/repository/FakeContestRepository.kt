package com.jae464.data.repository

import com.jae464.domain.model.Contest
import com.jae464.domain.repository.ContestRepository
import java.time.LocalDate
import javax.inject.Inject

class FakeContestRepository @Inject constructor() : ContestRepository {
    override suspend fun getCurrentContest(): Contest {
        return Contest(
            id = 1L,
            ingredient = "감자",
            imageUrl = "https://recipe1.ezmember.co.kr/cache/recipe/2022/09/23/5b162b0e112427972c379f0068a49d531.jpg",
            description = "이번 주 요리의 주 재료는 감자입니다! 다양한 감자 요리를 시도해보세요.",
            startDate = LocalDate.now()
        )
    }

    override suspend fun getAllContests(): List<Contest> {
        return listOf(
            Contest(
                id = 1L,
                ingredient = "감자",
                imageUrl = "https://recipe1.ezmember.co.kr/cache/recipe/2022/09/23/5b162b0e112427972c379f0068a49d531.jpg",
                description = "이번 주 요리의 주 재료는 감자입니다! 다양한 감자 요리를 시도해보세요.",
                startDate = LocalDate.now()
            ),
            Contest(
                id = 2L,
                ingredient = "오징어",
                imageUrl = "https://recipe1.ezmember.co.kr/cache/recipe/2022/09/23/5b162b0e112427972c379f0068a49d531.jpg",
                description = "이번 주 요리의 주 재료는 오징어입니다! 다양한 감자 요리를 시도해보세요.",
                startDate = LocalDate.now()
            ),
            Contest(
                id = 3L,
                ingredient = "두부",
                imageUrl = "https://recipe1.ezmember.co.kr/cache/recipe/2022/09/23/5b162b0e112427972c379f0068a49d531.jpg",
                description = "이번 주 요리의 주 재료는 두부입니다! 다양한 감자 요리를 시도해보세요.",
                startDate = LocalDate.now()
            ),
            Contest(
                id = 4L,
                ingredient = "미나리",
                imageUrl = "https://recipe1.ezmember.co.kr/cache/recipe/2022/09/23/5b162b0e112427972c379f0068a49d531.jpg",
                description = "이번 주 요리의 주 재료는 미나리입니다! 다양한 감자 요리를 시도해보세요.",
                startDate = LocalDate.now()
            )
        )
    }


}