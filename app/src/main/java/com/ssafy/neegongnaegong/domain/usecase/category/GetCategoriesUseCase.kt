package com.ssafy.neegongnaegong.domain.usecase.category

import com.ssafy.neegongnaegong.domain.repository.CategoryRepository

class GetCategoriesUseCase(
    private val categoryRepository: CategoryRepository,
) {
    suspend operator fun invoke() = categoryRepository.getCategories()
}
