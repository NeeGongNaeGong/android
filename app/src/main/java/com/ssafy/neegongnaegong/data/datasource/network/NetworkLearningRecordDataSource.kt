package com.ssafy.neegongnaegong.data.datasource.network

import com.ssafy.neegongnaegong.data.model.learningrecord.request.CreateLearningRecordRequest
import com.ssafy.neegongnaegong.data.model.learningrecord.request.GetLearningRecordRequest
import com.ssafy.neegongnaegong.data.model.learningrecord.request.UpdateLearningRecordRequest
import com.ssafy.neegongnaegong.data.model.learningrecord.response.CreateLearningRecordResponse
import com.ssafy.neegongnaegong.data.model.learningrecord.response.DeleteLearningRecordResponse
import com.ssafy.neegongnaegong.data.model.learningrecord.response.GetLearningRecordResponse
import com.ssafy.neegongnaegong.data.model.learningrecord.response.UpdateLearningRecordResponse
import kotlinx.coroutines.flow.Flow

interface NetworkLearningRecordDataSource {
    suspend fun updateLearningRecord(
        learningRecordId: Long,
        request: UpdateLearningRecordRequest,
    ): Flow<UpdateLearningRecordResponse>

    suspend fun deleteLearningRecord(learningRecordId: Long): Flow<DeleteLearningRecordResponse>

    suspend fun getLearningRecord(request: GetLearningRecordRequest): Flow<GetLearningRecordResponse>

    suspend fun createLearningRecord(request: CreateLearningRecordRequest): Flow<Long>
}
