package com.ssafy.neegongnaegong.data.repository

import com.ssafy.neegongnaegong.data.datasource.network.NetworkLearningRecordDataSource
import com.ssafy.neegongnaegong.data.model.learningrecord.request.CreateLearningRecordRequest
import com.ssafy.neegongnaegong.data.model.learningrecord.request.GetLearningRecordListRequest
import com.ssafy.neegongnaegong.data.model.learningrecord.request.UpdateLearningRecordRequest
import com.ssafy.neegongnaegong.data.model.learningrecord.response.CursorSliceResponse
import com.ssafy.neegongnaegong.domain.model.learning.LearningRecord
import com.ssafy.neegongnaegong.domain.repository.LearningRecordRepository
import com.ssafy.neegongnaegong.module.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LearningRecordRepositoryImpl
    @Inject
    constructor(
        private val dataSource: NetworkLearningRecordDataSource,
        @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    ) : LearningRecordRepository {
        override suspend fun getLearningRecord(learningRecordId: Long): Flow<LearningRecord> =
            withContext(ioDispatcher) {
                dataSource.getLearningRecord(learningRecordId = learningRecordId).map { it.toDomain() }
            }

        override suspend fun createLearningRecord(learningRecord: LearningRecord): Flow<Long> =
            withContext(ioDispatcher) {
                dataSource
                    .createLearningRecord(
                        request =
                            CreateLearningRecordRequest.fromDomain(
                                learningRecord,
                            ),
                    )
            }

        override suspend fun deleteLearningRecord(learningRecordId: Long): Flow<LearningRecord> {
            TODO("Not yet implemented")
        }

        override suspend fun updateLearningRecord(
            learningRecordId: Long,
            learningRecord: LearningRecord,
        ): Flow<Unit> =
            withContext(ioDispatcher) {
                dataSource.updateLearningRecord(
                    learningRecordId = learningRecordId,
                    request = UpdateLearningRecordRequest.fromDomain(learningRecord),
                )
            }

        override suspend fun getLearningRecordList(
            tag: List<Long>?,
            targetDate: String?,
            cursorCreatedAt: String?,
            cursorId: Long?,
            size: Int,
        ): Flow<CursorSliceResponse> =
            withContext(ioDispatcher) {
                dataSource
                    .getLearningRecordList(
                        GetLearningRecordListRequest(
                            tag = tag,
                            targetDate = targetDate,
                            cursorCreatedAt = cursorCreatedAt,
                            cursorId = cursorId,
                            size = size,
                        ),
                    ).map { slice ->
                        CursorSliceResponse(
                            content = slice.content,
                            hasNext = slice.hasNext,
                            cursorCreatedAt = slice.cursorCreatedAt,
                            cursorId = slice.cursorId,
                        )
                    }
            }
    }
