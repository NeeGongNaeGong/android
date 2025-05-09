package com.ssafy.neegongnaegong.data.datasource.network

import com.ssafy.neegongnaegong.data.model.apiFlow
import com.ssafy.neegongnaegong.data.model.studies.request.CreateVoteRequest
import com.ssafy.neegongnaegong.data.remote.StudiesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NetworkStudiesDataSourceImpl @Inject constructor(
    private val studiesApi: StudiesApi
) : NetworkStudiesDataSource {


    override suspend fun createVote(studyId: Int, requestBody: CreateVoteRequest): Flow<String> =
        apiFlow { studiesApi.createVote(studyId, requestBody) }
}
