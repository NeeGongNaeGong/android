package com.ssafy.neegongnaegong.domain.usecase.studygroup

import androidx.paging.PagingData
import com.ssafy.neegongnaegong.domain.model.studygroup.VoteHistoryInfo
import com.ssafy.neegongnaegong.domain.repository.StudyGroupRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetVoteListUseCase
    @Inject
    constructor(
        private val repository: StudyGroupRepository,
    ) {
        operator fun invoke(request: Long): Flow<PagingData<VoteHistoryInfo>> = repository.getVoteList(request)
    }
