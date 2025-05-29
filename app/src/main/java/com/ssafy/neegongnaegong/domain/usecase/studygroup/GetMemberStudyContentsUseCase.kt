package com.ssafy.neegongnaegong.domain.usecase.studygroup

import androidx.paging.PagingData
import com.ssafy.neegongnaegong.domain.model.studygroup.StudyContentInfo
import com.ssafy.neegongnaegong.domain.model.studygroup.StudyMemberInfo
import com.ssafy.neegongnaegong.domain.repository.StudyGroupRepository
import kotlinx.coroutines.flow.Flow

class GetMemberStudyContentsUseCase(
    private val repository: StudyGroupRepository,
) {
    operator fun invoke(request: StudyMemberInfo): Flow<PagingData<StudyContentInfo>> =
        repository.getMemberStudyContents(request)
}
