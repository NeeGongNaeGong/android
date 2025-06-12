package com.ssafy.neegongnaegong.data.repository

import com.ssafy.neegongnaegong.data.datasource.local.LocalFileDataSource
import com.ssafy.neegongnaegong.domain.repository.LocalFileRepository
import com.ssafy.neegongnaegong.module.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.RequestBody
import javax.inject.Inject

class LocalFileRepositoryImpl
    @Inject
    constructor(
        private val localFileDataSource: LocalFileDataSource,
        @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    ) : LocalFileRepository {
        override fun getFileType(url: String): Flow<String?> =
            localFileDataSource
                .getFileType(url = url)
                .flowOn(context = ioDispatcher)

        override fun getRequestBody(url: String): Flow<RequestBody?> =
            localFileDataSource
                .getRequestBody(url = url)
                .flowOn(context = ioDispatcher)
    }
