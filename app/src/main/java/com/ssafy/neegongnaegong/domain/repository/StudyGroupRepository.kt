package com.ssafy.neegongnaegong.domain.repository

import androidx.paging.PagingData
import com.ssafy.neegongnaegong.domain.model.studygroup.StudyContentInfo
import com.ssafy.neegongnaegong.domain.model.studygroup.StudyLogByTagInfo
import com.ssafy.neegongnaegong.domain.model.studygroup.StudyMemberInfo
import kotlinx.coroutines.flow.Flow

interface StudyGroupRepository {
    fun getMemberStudyLogsByTag(request: StudyMemberInfo): Flow<List<StudyLogByTagInfo>>

    fun getMemberStudyContents(request: StudyMemberInfo): Flow<PagingData<StudyContentInfo>>

    fun approveStudyGroupJoin(
        studyGroupId: Long,
        userId: Long,
        notificationId: Long?,
    ): Flow<Unit>

    fun rejectStudyGroupJoin(
        studyGroupId: Long,
        userId: Long,
        notificationId: Long?,
    ): Flow<Unit>
}
