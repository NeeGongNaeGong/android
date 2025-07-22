package com.ssafy.neegongnaegong.domain.usecase.learningrecord

import com.ssafy.neegongnaegong.domain.repository.LearningRecordRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class DeleteSelectedLearningRecordsUseCase
    @Inject
    constructor(
        private val repository: LearningRecordRepository,
    ) {
        operator fun invoke(recordIds: List<Long>): Flow<Unit> = repository.deleteSelectedLearningRecords(recordIds)
    }
