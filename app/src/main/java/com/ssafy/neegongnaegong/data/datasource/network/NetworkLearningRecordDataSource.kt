package com.ssafy.neegongnaegong.data.datasource.network

import com.ssafy.neegongnaegong.data.model.learningrecord.request.CreateLearningRecordRequest
import com.ssafy.neegongnaegong.data.model.learningrecord.request.DeleteSelectedLearningRecordsRequest
import com.ssafy.neegongnaegong.data.model.learningrecord.request.GetLearningRecordListRequest
import com.ssafy.neegongnaegong.data.model.learningrecord.request.UpdateLearningRecordRequest
import com.ssafy.neegongnaegong.data.model.learningrecord.response.CursorSliceResponse
import com.ssafy.neegongnaegong.data.model.learningrecord.response.GetLearningRecordDatesByMonthResponse
import com.ssafy.neegongnaegong.data.model.learningrecord.response.GetLearningRecordResponse
import kotlinx.coroutines.flow.Flow

interface NetworkLearningRecordDataSource {
    fun updateLearningRecord(
        learningRecordId: Long,
        request: UpdateLearningRecordRequest,
    ): Flow<Unit>

    fun deleteLearningRecord(learningRecordId: Long): Flow<Unit>

    fun deleteSelectedLearningRecords(request: DeleteSelectedLearningRecordsRequest): Flow<Unit>

    fun getLearningRecord(learningRecordId: Long): Flow<GetLearningRecordResponse>

    fun createLearningRecord(request: CreateLearningRecordRequest): Flow<Long>

    fun getLearningRecordList(request: GetLearningRecordListRequest): Flow<CursorSliceResponse>

    fun getLearningRecordDatesByMonth(yearMonth: String): Flow<GetLearningRecordDatesByMonthResponse>
}
