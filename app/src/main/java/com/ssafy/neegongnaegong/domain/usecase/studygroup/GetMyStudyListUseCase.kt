package com.ssafy.neegongnaegong.domain.usecase.studygroup

import androidx.paging.PagingData
import com.ssafy.neegongnaegong.domain.model.studygroup.MyStudyGroupInfo
import com.ssafy.neegongnaegong.domain.repository.StudyGroupRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMyStudyListUseCase
    @Inject
    constructor(
        private val repository: StudyGroupRepository,
    ) {
        operator fun invoke(size: Int = 10): Flow<PagingData<MyStudyGroupInfo>> = repository.getMyStudyGroupList(size)
    }
