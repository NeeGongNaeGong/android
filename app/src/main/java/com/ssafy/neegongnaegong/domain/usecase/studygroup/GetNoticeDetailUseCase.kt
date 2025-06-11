package com.ssafy.neegongnaegong.domain.usecase.studygroup

import com.ssafy.neegongnaegong.domain.model.studygroup.StudyGroupNoticeDetailInfo
import com.ssafy.neegongnaegong.domain.repository.StudyGroupRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNoticeDetailUseCase
    @Inject
    constructor(
        private val repository: StudyGroupRepository,
    ) {
        operator fun invoke(
            studyGroupId: Long,
            noticeId: Long,
        ): Flow<StudyGroupNoticeDetailInfo> = repository.getNoticeDetail(studyGroupId, noticeId)
    }
