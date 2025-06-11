package com.ssafy.neegongnaegong.domain.usecase.studies

import com.ssafy.neegongnaegong.domain.model.studies.VoteInfo
import com.ssafy.neegongnaegong.domain.repository.StudiesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreateVoteUseCase
    @Inject
    constructor(
        private val studiesRepository: StudiesRepository,
    ) {
        suspend operator fun invoke(
            studyGroupId: Long,
            voteInfo: VoteInfo,
        ): Flow<Unit> {
            return studiesRepository.createVote(studyGroupId, voteInfo)
        }
    }
