package com.ssafy.neegongnaegong.data.datasource.network

import com.ssafy.neegongnaegong.data.model.apiFlow
import com.ssafy.neegongnaegong.data.model.learningrecord.request.CreateLearningRecordRequest
import com.ssafy.neegongnaegong.data.model.learningrecord.request.GetLearningRecordRequest
import com.ssafy.neegongnaegong.data.model.learningrecord.request.UpdateLearningRecordRequest
import com.ssafy.neegongnaegong.data.model.learningrecord.response.CreateLearningRecordResponse
import com.ssafy.neegongnaegong.data.model.learningrecord.response.DeleteLearningRecordResponse
import com.ssafy.neegongnaegong.data.model.learningrecord.response.GetLearningRecordResponse
import com.ssafy.neegongnaegong.data.model.learningrecord.response.UpdateLearningRecordResponse
import com.ssafy.neegongnaegong.data.remote.LearningRecordApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NetworkLearningRecordDataSourceImpl
    @Inject
    constructor(
        private val api: LearningRecordApi,
    ) : NetworkLearningRecordDataSource {
        override suspend fun updateLearningRecord(
            learningRecordId: Long,
            request: UpdateLearningRecordRequest,
        ): Flow<UpdateLearningRecordResponse> = apiFlow { api.updateLearningRecord(learningRecordId, request) }

        override suspend fun deleteLearningRecord(learningRecordId: Long): Flow<DeleteLearningRecordResponse> =
            apiFlow { api.deleteLearningRecord(learningRecordId) }

        override suspend fun getLearningRecord(request: GetLearningRecordRequest): Flow<GetLearningRecordResponse> =
            apiFlow { api.getLearningRecord(request) }

        override suspend fun createLearningRecord(request: CreateLearningRecordRequest): Flow<CreateLearningRecordResponse> =
            apiFlow { api.createLearningRecord(request) }
    }
