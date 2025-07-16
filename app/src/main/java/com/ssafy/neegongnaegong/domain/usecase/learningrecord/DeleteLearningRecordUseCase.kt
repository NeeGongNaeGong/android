package com.ssafy.neegongnaegong.domain.usecase.learningrecord

import com.ssafy.neegongnaegong.domain.repository.LearningRecordRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class DeleteLearningRecordUseCase
    @Inject
    constructor(
        private val learningRecordRepository: LearningRecordRepository,
    ) {
        suspend operator fun invoke(learningRecordId: Long): Flow<Unit> = learningRecordRepository.deleteLearningRecord(learningRecordId)
    }
