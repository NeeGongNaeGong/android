package com.ssafy.neegongnaegong.data.datasource.network

import com.ssafy.neegongnaegong.data.model.apiFlow
import com.ssafy.neegongnaegong.data.model.learningrecord.request.CreateLearningRecordRequest
import com.ssafy.neegongnaegong.data.model.learningrecord.request.GetLearningRecordListRequest
import com.ssafy.neegongnaegong.data.model.learningrecord.request.UpdateLearningRecordRequest
import com.ssafy.neegongnaegong.data.model.learningrecord.response.CursorSliceResponse
import com.ssafy.neegongnaegong.data.model.learningrecord.response.DeleteLearningRecordResponse
import com.ssafy.neegongnaegong.data.model.learningrecord.response.GetLearningRecordResponse
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
        ): Flow<Unit> = apiFlow { api.updateLearningRecord(learningRecordId, request) }

        override suspend fun deleteLearningRecord(learningRecordId: Long): Flow<DeleteLearningRecordResponse> =
            apiFlow { api.deleteLearningRecord(learningRecordId) }

        override suspend fun getLearningRecord(learningRecordId: Long): Flow<GetLearningRecordResponse> =
            apiFlow { api.getLearningRecord(learningRecordId) }

        override suspend fun createLearningRecord(request: CreateLearningRecordRequest): Flow<Long> =
            apiFlow { api.createLearningRecord(request) }

        override suspend fun getLearningRecordList(request: GetLearningRecordListRequest): Flow<CursorSliceResponse> =
            apiFlow {
                api.getLearningRecordList(
                    tag = request.tag,
                    targetDate = request.targetDate,
                    cursorCreatedAt = request.cursorCreatedAt,
                    cursorId = request.cursorId,
                    size = request.size,
                )
            }
    }
