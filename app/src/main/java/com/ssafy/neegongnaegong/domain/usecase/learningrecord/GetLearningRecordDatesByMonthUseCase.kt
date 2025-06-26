package com.ssafy.neegongnaegong.domain.usecase.learningrecord

import com.ssafy.neegongnaegong.domain.repository.LearningRecordRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class GetLearningRecordDatesByMonthUseCase
    @Inject
    constructor(
        private val learningRecordRepository: LearningRecordRepository,
    ) {
        operator fun invoke(yearMonth: String): Flow<List<LocalDate>> = learningRecordRepository.getLearningRecordDatesByMonth(yearMonth)
    }
