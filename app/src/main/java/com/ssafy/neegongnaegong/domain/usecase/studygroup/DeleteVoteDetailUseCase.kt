package com.ssafy.neegongnaegong.domain.usecase.studygroup

import com.ssafy.neegongnaegong.domain.repository.StudyGroupRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteVoteDetailUseCase
    @Inject
    constructor(
        private val repository: StudyGroupRepository,
    ) {
        operator fun invoke(
            studyGroupId: Long,
            voteId: Long,
        ): Flow<Unit> = repository.deleteVoteDetail(studyGroupId, voteId)
    }
