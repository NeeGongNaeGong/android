package com.ssafy.neegongnaegong.data.datasource.network

import com.ssafy.neegongnaegong.data.model.apiFlow
import com.ssafy.neegongnaegong.data.model.learningrecord.request.CreateLearningRecordRequest
import com.ssafy.neegongnaegong.data.model.learningrecord.request.DeleteSelectedLearningRecordsRequest
import com.ssafy.neegongnaegong.data.model.learningrecord.request.GetLearningRecordListRequest
import com.ssafy.neegongnaegong.data.model.learningrecord.request.UpdateLearningRecordRequest
import com.ssafy.neegongnaegong.data.model.learningrecord.response.CursorSliceResponse
import com.ssafy.neegongnaegong.data.model.learningrecord.response.GetLearningRecordDatesByMonthResponse
import com.ssafy.neegongnaegong.data.model.learningrecord.response.GetLearningRecordResponse
import com.ssafy.neegongnaegong.data.remote.LearningRecordApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NetworkLearningRecordDataSourceImpl
    @Inject
    constructor(
        private val api: LearningRecordApi,
    ) : NetworkLearningRecordDataSource {
        override fun updateLearningRecord(
            learningRecordId: Long,
            request: UpdateLearningRecordRequest,
        ): Flow<Unit> = apiFlow { api.updateLearningRecord(learningRecordId, request) }

        override fun deleteLearningRecord(learningRecordId: Long): Flow<Unit> = apiFlow { api.deleteLearningRecord(learningRecordId) }

        override fun deleteSelectedLearningRecords(request: DeleteSelectedLearningRecordsRequest): Flow<Unit> =
            apiFlow { api.deleteSelectedLearningRecords(request = request) }

        override fun getLearningRecord(learningRecordId: Long): Flow<GetLearningRecordResponse> =
            apiFlow { api.getLearningRecord(learningRecordId) }

        override fun createLearningRecord(request: CreateLearningRecordRequest): Flow<Long> = apiFlow { api.createLearningRecord(request) }

        override fun getLearningRecordList(request: GetLearningRecordListRequest): Flow<CursorSliceResponse> =
            apiFlow {
                api.getLearningRecordList(
                    tag = request.tag,
                    targetDate = request.targetDate,
                    cursorValue = request.cursorValue,
                    cursorId = request.cursorId,
                    size = request.size,
                )
            }

        override fun getLearningRecordDatesByMonth(yearMonth: String): Flow<GetLearningRecordDatesByMonthResponse> =
            apiFlow {
                api.getLearningRecordDatesByMonth(
                    yearMonth = yearMonth,
                )
            }
    }
