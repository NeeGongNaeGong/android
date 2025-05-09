package com.ssafy.neegongnaegong.domain.usecase.category

import com.ssafy.neegongnaegong.domain.repository.CategoryRepository

class GetTagsUseCase(
    private val categoryRepository: CategoryRepository,
) {
    suspend operator fun invoke(categoryId: Long) =
        categoryRepository.getTags(
            categoryId,
        )
}
