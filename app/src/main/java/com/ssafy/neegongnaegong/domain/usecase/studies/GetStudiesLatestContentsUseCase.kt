package com.ssafy.neegongnaegong.domain.usecase.studies

import com.ssafy.neegongnaegong.domain.model.studies.combination.StudiesContentsWithReadStatus
import com.ssafy.neegongnaegong.domain.repository.StudiesRepository
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetStudiesLatestContentsUseCase
    @Inject
    constructor(
        private val repository: StudiesRepository,
    ) {
        operator fun invoke(studyGroupId: Long) =
            combine(
                repository.getStudiesLatestContents(studyGroupId),
                repository.getStudiesLatestContentsReadStatus(studyGroupId),
            ) { contents, readStatus ->
                StudiesContentsWithReadStatus(
                    contents = contents,
                    readStatus = readStatus,
                )
            }
    }
