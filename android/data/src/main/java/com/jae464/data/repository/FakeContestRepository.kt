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
                description = "부드럽고 담백한 맛이 매력적인 감자를 활용해보세요.",
                startDate = LocalDate.now()
            ),
            Contest(
                id = 2L,
                ingredient = "오징어",
                imageUrl = "https://static.megamart.com/product/image/P000/P0000220/P0000220_6_960.jpg",
                description = "쫄깃한 식감이 일품인 오징어로 다양한 요리를 즐겨보세요.",
                startDate = LocalDate.now().minusWeeks(1L)
            ),
            Contest(
                id = 3L,
                ingredient = "두부",
                imageUrl = "https://sahubconn001.blob.core.windows.net/ct-sahubconn001/img/newshop/goods/016854/016854_1.jpg",
                description = "부드럽고 건강한 두부를 이용한 요리를 시도해보세요.",
                startDate = LocalDate.now().minusWeeks(2L)
            ),
            Contest(
                id = 4L,
                ingredient = "미나리",
                imageUrl = "https://www.100ssd.co.kr/news/photo/202305/98443_78279_2814.jpg",
                description = "상큼하고 향긋한 미나리로 신선한 요리를 만들어보세요.",
                startDate = LocalDate.now().minusWeeks(3L)
            )
        )

    }


}