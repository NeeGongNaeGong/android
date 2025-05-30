package com.ssafy.neegongnaegong.domain.usecase.studygroup

import com.ssafy.neegongnaegong.domain.model.studygroup.StudyLogByTagInfo
import com.ssafy.neegongnaegong.domain.model.studygroup.StudyMemberInfo
import com.ssafy.neegongnaegong.domain.repository.StudyGroupRepository
import kotlinx.coroutines.flow.Flow

class GetMemberStudyLogsByTagUseCase(
    private val repository: StudyGroupRepository,
) {
    operator fun invoke(request: StudyMemberInfo): Flow<List<StudyLogByTagInfo>> = repository.getMemberStudyLogsByTag(request)
}
