package com.ssafy.neegongnaegong.domain.repository

import com.ssafy.neegongnaegong.data.model.learningrecord.response.CursorSlice
import com.ssafy.neegongnaegong.domain.model.learning.LearningRecord
import kotlinx.coroutines.flow.Flow

interface LearningRecordRepository {
    suspend fun getLearningRecord(learningRecordId: Long): Flow<LearningRecord>

    suspend fun createLearningRecord(learningRecord: LearningRecord): Flow<Long>

    suspend fun deleteLearningRecord(learningRecordId: Long): Flow<LearningRecord>

    suspend fun updateLearningRecord(
        learningRecordId: Long,
        learningRecord: LearningRecord,
    ): Flow<Unit>

    suspend fun getLearningRecordList(
        tag: List<Long>? = null,
        targetDate: String? = null,
        cursorCreatedAt: String? = null,
        cursorId: Long? = null,
        size: Int = 20,
    ): Flow<CursorSlice>
}
