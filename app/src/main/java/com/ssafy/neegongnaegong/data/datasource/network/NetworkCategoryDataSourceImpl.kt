package com.ssafy.neegongnaegong.data.datasource.network

import com.ssafy.neegongnaegong.data.model.apiFlow
import com.ssafy.neegongnaegong.data.remote.CategoryApi
import com.ssafy.neegongnaegong.domain.model.studies.Category
import com.ssafy.neegongnaegong.domain.model.studies.Tag
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NetworkCategoryDataSourceImpl
    @Inject
    constructor(
        private val api: CategoryApi,
    ) : NetworkCategoryDataSource {
        override suspend fun getCategories(): Flow<List<Category>> =
            apiFlow {
                api.getCategories()
            }

        override suspend fun getTags(categoryId: Long): Flow<List<Tag>> =
            apiFlow {
                api.getTags(categoryId)
            }
    }
