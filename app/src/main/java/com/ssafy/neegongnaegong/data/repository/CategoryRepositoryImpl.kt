package com.ssafy.neegongnaegong.data.repository

import com.ssafy.neegongnaegong.data.datasource.network.NetworkCategoryDataSource
import com.ssafy.neegongnaegong.domain.model.studies.Category
import com.ssafy.neegongnaegong.domain.model.studies.Tag
import com.ssafy.neegongnaegong.domain.repository.CategoryRepository
import com.ssafy.neegongnaegong.module.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CategoryRepositoryImpl
    @Inject
    constructor(
        private val dataSource: NetworkCategoryDataSource,
        @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    ) : CategoryRepository {
        override suspend fun getCategories(): Flow<List<Category>> =
            withContext(ioDispatcher) {
                dataSource.getCategories()
            }

        override suspend fun getTags(categoryId: Long): Flow<List<Tag>> =
            withContext(ioDispatcher) {
                dataSource.getTags(categoryId)
            }
    }
