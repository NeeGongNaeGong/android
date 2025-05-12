package com.ssafy.neegongnaegong.data.datasource.network

import com.ssafy.neegongnaegong.data.model.ApiResponse
import com.ssafy.neegongnaegong.data.model.studygroup.response.MemberWeeklyStudyContentBySliceResponse
import com.ssafy.neegongnaegong.data.model.studygroup.response.StudyLogByTagResponse
import com.ssafy.neegongnaegong.domain.model.studygroup.MemberStudyContentsInfo
import com.ssafy.neegongnaegong.domain.model.studygroup.StudyMemberInfo
import kotlinx.collections.immutable.PersistentList
import kotlinx.coroutines.flow.Flow

interface NetworkStudyGroupDataSource {
    fun getMemberStudyLogsByTag(request: StudyMemberInfo): Flow<PersistentList<StudyLogByTagResponse>>

    suspend fun getMemberStudyContents(request: MemberStudyContentsInfo): Result<ApiResponse<MemberWeeklyStudyContentBySliceResponse>>
}
