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
        cursorValue: String? = null,
        cursorId: Long? = null,
        size: Int = 10,
    ): Flow<CursorSliceResponse> =
        repository.getLearningRecordList(
            tag = tag,
            targetDate = targetDate,
            cursorValue = cursorValue,
            cursorId = cursorId,
            size = size,
        )
}
