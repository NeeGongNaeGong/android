package com.ssafy.neegongnaegong.data.datasource.network

import com.ssafy.neegongnaegong.data.model.learningrecord.request.CreateLearningRecordRequest
import com.ssafy.neegongnaegong.data.model.learningrecord.request.GetLearningRecordListRequest
import com.ssafy.neegongnaegong.data.model.learningrecord.request.UpdateLearningRecordRequest
import com.ssafy.neegongnaegong.data.model.learningrecord.response.CursorSliceResponse
import com.ssafy.neegongnaegong.data.model.learningrecord.response.DeleteLearningRecordResponse
import com.ssafy.neegongnaegong.data.model.learningrecord.response.GetLearningRecordDatesByMonthResponse
import com.ssafy.neegongnaegong.data.model.learningrecord.response.GetLearningRecordResponse
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.YearMonth

interface NetworkLearningRecordDataSource {
    fun updateLearningRecord(
        learningRecordId: Long,
        request: UpdateLearningRecordRequest,
    ): Flow<Unit>

    fun deleteLearningRecord(learningRecordId: Long): Flow<DeleteLearningRecordResponse>

    fun getLearningRecord(learningRecordId: Long): Flow<GetLearningRecordResponse>

    fun createLearningRecord(request: CreateLearningRecordRequest): Flow<Long>

    fun getLearningRecordList(request: GetLearningRecordListRequest): Flow<CursorSliceResponse>

    fun getLearningRecordDatesByMonth(yearMonth: String): Flow<GetLearningRecordDatesByMonthResponse>
}
