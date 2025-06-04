package com.ssafy.neegongnaegong.domain.usecase.studygroup

import androidx.paging.PagingData
import com.ssafy.neegongnaegong.domain.model.studygroup.NoticeHistoryInfo
import com.ssafy.neegongnaegong.domain.repository.StudyGroupRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNoticeListUseCase
    @Inject
    constructor(
        private val repository: StudyGroupRepository,
    ) {
        operator fun invoke(request: Long): Flow<PagingData<NoticeHistoryInfo>> = repository.getNoticeList(request)
    }
