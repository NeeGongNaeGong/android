package com.ssafy.neegongnaegong.data.repository

import com.ssafy.neegongnaegong.data.datasource.network.NetworkS3DataSource
import com.ssafy.neegongnaegong.domain.repository.S3Repository
import com.ssafy.neegongnaegong.module.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import okhttp3.RequestBody
import javax.inject.Inject

class S3RepositoryImpl
    @Inject
    constructor(
        private val dataSource: NetworkS3DataSource,
        @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    ) : S3Repository {
        override suspend fun uploadImageToS3(
            presignedUrl: String,
            request: RequestBody,
        ) {
            withContext(ioDispatcher) {
                dataSource.uploadImageToS3(
                    presignedUrl,
                    request,
                )
            }
        }
    }
