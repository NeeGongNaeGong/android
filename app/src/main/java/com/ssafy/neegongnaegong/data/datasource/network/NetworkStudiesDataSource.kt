package com.ssafy.neegongnaegong.data.datasource.network

import com.ssafy.neegongnaegong.data.model.studies.request.CreateNoticeRequest
import com.ssafy.neegongnaegong.data.model.studies.request.CreateVoteRequest
import kotlinx.coroutines.flow.Flow

interface NetworkStudiesDataSource {
    fun createVote(
        studyGroupId: Long,
        requestBody: CreateVoteRequest,
    ): Flow<Unit>

    fun createNotice(
        studyGroupId: Long,
        requestBody: CreateNoticeRequest,
    ): Flow<Unit>
}
