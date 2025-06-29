package com.ssafy.neegongnaegong.domain.usecase.studies

import com.ssafy.neegongnaegong.domain.repository.StudiesRepository
import javax.inject.Inject

class PatchStudiesLatestContentsReadStatusUseCase
    @Inject
    constructor(
        private val repository: StudiesRepository,
    ) {
        operator fun invoke(
            studyGroupId: Long,
            readNotice: Boolean?,
            readVote: Boolean?,
        ) = repository.patchStudiesLatestContentsReadStatus(
            studyGroupId = studyGroupId,
            readNotice = readNotice,
            readVote = readVote,
        )
    }
