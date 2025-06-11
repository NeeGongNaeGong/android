package com.ssafy.neegongnaegong.domain.usecase.studygroup

import com.ssafy.neegongnaegong.domain.repository.StudyGroupRepository
import javax.inject.Inject

class AddNewVoteOptionUseCase
    @Inject
    constructor(
        private val studyGroupRepository: StudyGroupRepository,
    ) {
        operator fun invoke(
            studyGroupId: Long,
            voteId: Long,
            voteItem: String,
        ) = studyGroupRepository.addNewVoteOption(studyGroupId, voteId, voteItem)
    }
