package com.ssafy.neegongnaegong.data.datasource.network

import com.ssafy.neegongnaegong.data.model.studies.request.CreateVoteRequest
import com.ssafy.neegongnaegong.data.model.user.response.UserDetailResponse
import kotlinx.coroutines.flow.Flow

interface NetworkStudiesDataSource {
    suspend fun createVote(studyId: Int, requestBody: CreateVoteRequest): Flow<Unit>
}
