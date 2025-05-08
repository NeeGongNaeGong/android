package com.ssafy.neegongnaegong.domain.repository

import com.ssafy.neegongnaegong.domain.model.learning.LearningRecord
import kotlinx.coroutines.flow.Flow

interface LearningRecordRepository {
    suspend fun getLearningRecords(userId: Long): List<LearningRecord>

    suspend fun createLearningRecord(learningRecord: LearningRecord): Flow<Long>

    suspend fun deleteLearningRecord(learningRecordId: Long): Flow<LearningRecord>

    suspend fun updateLearningRecord(
        learningRecordId: Long,
        learningRecord: LearningRecord,
    ): Flow<LearningRecord>
}
