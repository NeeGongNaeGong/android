package com.ssafy.neegongnaegong.domain.usecase.studygroup

import com.ssafy.neegongnaegong.domain.model.studygroup.StudyGroupDetailInfo
import com.ssafy.neegongnaegong.domain.repository.StudyGroupRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetStudyGroupDetailUseCase
    @Inject
    constructor(
        private val studyGroupRepository: StudyGroupRepository,
    ) {
        operator fun invoke(studyGroupId: Long): Flow<StudyGroupDetailInfo> = studyGroupRepository.getStudyGroupDetail(studyGroupId)
    }
