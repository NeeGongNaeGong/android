package com.ssafy.neegongnaegong.data.repository

import com.ssafy.neegongnaegong.data.datasource.network.NetworkFileDataSource
import com.ssafy.neegongnaegong.data.model.file.request.PostIssuePresignedUrlRequest
import com.ssafy.neegongnaegong.domain.model.file.PresignedUrlInfo
import com.ssafy.neegongnaegong.domain.repository.FileRepository
import com.ssafy.neegongnaegong.module.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FileRepositoryImpl
    @Inject
    constructor(
        private val dataSource: NetworkFileDataSource,
        @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    ) : FileRepository {
        override suspend fun issuePresignedUrl(
            id: Long,
            uploadPathType: String,
            imageExtension: String,
        ): Flow<PresignedUrlInfo> =
            withContext(ioDispatcher) {
                dataSource
                    .issuePresignedUrl(
                        PostIssuePresignedUrlRequest(
                            id,
                            uploadPathType,
                            imageExtension,
                        ),
                    ).map { it.toDomain() }
            }
    }
