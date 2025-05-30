package com.ssafy.neegongnaegong.domain.usecase.learningrecord

import com.ssafy.neegongnaegong.data.model.learningrecord.response.CursorSliceResponse
import com.ssafy.neegongnaegong.domain.repository.LearningRecordRepository
import kotlinx.coroutines.flow.Flow

class GetLearningRecordListUseCase(
    private val repository: LearningRecordRepository,
) {
    suspend operator fun invoke(
        tag: List<Long>? = null,
        targetDate: String? = null,
        cursorCreatedAt: String? = null,
        cursorId: Long? = null,
        size: Int = 30,
    ): Flow<CursorSliceResponse> =
        repository.getLearningRecordList(
            tag = tag,
            targetDate = targetDate,
            cursorCreatedAt = cursorCreatedAt,
            cursorId = cursorId,
            size = size,
        )
}
