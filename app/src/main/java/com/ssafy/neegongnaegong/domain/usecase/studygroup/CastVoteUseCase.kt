package com.ssafy.neegongnaegong.domain.usecase.studygroup

import com.ssafy.neegongnaegong.domain.repository.StudyGroupRepository
import javax.inject.Inject

class CastVoteUseCase
    @Inject
    constructor(
        private val studyGroupRepository: StudyGroupRepository,
    ) {
        operator fun invoke(
            studyGroupId: Long,
            voteId: Long,
            voteItems: List<String>,
        ) = studyGroupRepository.castVote(studyGroupId, voteId, voteItems)
    }
