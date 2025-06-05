package com.ssafy.neegongnaegong.data.datasource.network

import com.ssafy.neegongnaegong.data.model.apiFlow
import com.ssafy.neegongnaegong.data.model.studies.request.CreateNoticeRequest
import com.ssafy.neegongnaegong.data.model.studies.request.CreateVoteRequest
import com.ssafy.neegongnaegong.data.remote.StudiesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkStudiesDataSourceImpl
    @Inject
    constructor(
        private val studiesApi: StudiesApi,
    ) : NetworkStudiesDataSource {
        override suspend fun createVote(
            studyGroupId: Long,
            requestBody: CreateVoteRequest,
        ): Flow<Unit> = apiFlow { studiesApi.createVote(studyGroupId, requestBody) }

        override fun createNotice(
            studyGroupId: Long,
            requestBody: CreateNoticeRequest,
        ): Flow<Unit> =
            apiFlow {
                studiesApi.createNotice(studyGroupId, requestBody)
            }
    }
