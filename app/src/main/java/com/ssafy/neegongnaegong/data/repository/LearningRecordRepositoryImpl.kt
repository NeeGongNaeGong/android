package com.ssafy.neegongnaegong.data.repository

import com.ssafy.neegongnaegong.data.datasource.network.NetworkLearningRecordDataSource
import com.ssafy.neegongnaegong.domain.model.learning.LearningRecord
import com.ssafy.neegongnaegong.domain.repository.LearningRecordRepository
import com.ssafy.neegongnaegong.module.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LearningRecordRepositoryImpl
    @Inject
    constructor(
        private val dateSource: NetworkLearningRecordDataSource,
        @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    ) : LearningRecordRepository {
        override suspend fun getLearningRecords(userId: Long): List<LearningRecord> {
            TODO("Not yet implemented")
        }

        override suspend fun createLearningRecord(learningRecord: LearningRecord): Flow<LearningRecord> {
            TODO("Not yet implemented")
        }

        override suspend fun deleteLearningRecord(learningRecordId: Long): Flow<LearningRecord> {
            TODO("Not yet implemented")
        }

        override suspend fun updateLearningRecord(
            learningRecordId: Long,
            learningRecord: LearningRecord,
        ): Flow<LearningRecord> {
            TODO("Not yet implemented")
        }
    }
