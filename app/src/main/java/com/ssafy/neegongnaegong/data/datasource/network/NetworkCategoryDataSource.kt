package com.ssafy.neegongnaegong.data.datasource.network

import com.ssafy.neegongnaegong.domain.model.studies.Category
import com.ssafy.neegongnaegong.domain.model.studies.Tag
import kotlinx.coroutines.flow.Flow

interface NetworkCategoryDataSource {
    suspend fun getCategories(): Flow<List<Category>>

    suspend fun getTags(categoryId: Long): Flow<List<Tag>>
}
