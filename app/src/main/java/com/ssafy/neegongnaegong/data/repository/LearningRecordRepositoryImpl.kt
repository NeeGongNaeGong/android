package com.ssafy.neegongnaegong.data.repository

import com.ssafy.neegongnaegong.data.datasource.network.NetworkLearningRecordDataSource
import com.ssafy.neegongnaegong.data.mapper.learningrecord.LearningRecordMapper.toCreateRequest
import com.ssafy.neegongnaegong.data.mapper.learningrecord.LearningRecordMapper.toDomain
import com.ssafy.neegongnaegong.data.mapper.learningrecord.LearningRecordMapper.toLocalDates
import com.ssafy.neegongnaegong.data.mapper.learningrecord.LearningRecordMapper.toUpdateRequest
import com.ssafy.neegongnaegong.data.model.learningrecord.request.GetLearningRecordListRequest
import com.ssafy.neegongnaegong.data.model.learningrecord.response.CursorSliceResponse
import com.ssafy.neegongnaegong.domain.model.learning.LearningRecord
import com.ssafy.neegongnaegong.domain.repository.LearningRecordRepository
import com.ssafy.neegongnaegong.module.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.time.LocalDate
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
                dataSource.createLearningRecord(request = learningRecord.toCreateRequest())
            }

        override suspend fun deleteLearningRecord(learningRecordId: Long): Flow<Unit> =
            dataSource
                .deleteLearningRecord(
                    learningRecordId = learningRecordId,
                ).flowOn(context = ioDispatcher)

        override suspend fun updateLearningRecord(
            learningRecordId: Long,
            learningRecord: LearningRecord,
        ): Flow<Unit> =
            withContext(ioDispatcher) {
                dataSource.updateLearningRecord(
                    learningRecordId = learningRecordId,
                    request = learningRecord.toUpdateRequest(),
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

        override fun getLearningRecordDatesByMonth(yearMonth: String): Flow<List<LocalDate>> =
            dataSource
                .getLearningRecordDatesByMonth(yearMonth = yearMonth)
                .map { it.toLocalDates() }
                .flowOn(context = ioDispatcher)
    }
