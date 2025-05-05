package com.ssafy.neegongnaegong.domain.usecase.learningrecord

import com.ssafy.neegongnaegong.domain.model.learning.LearningRecord
import com.ssafy.neegongnaegong.domain.repository.LearningRecordRepository
import kotlinx.coroutines.flow.Flow

class DeleteLearningRecordUseCase(private val learningRecordRepository: LearningRecordRepository) {
    suspend operator fun invoke(learningRecordId: Long): Flow<LearningRecord>{
        return learningRecordRepository.deleteLearningRecord(learningRecordId)
    }
}
