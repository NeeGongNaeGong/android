package com.ssafy.neegongnaegong.data.datasource.network

import com.ssafy.neegongnaegong.domain.studygroup.MemberStudyContentsInfo
import com.ssafy.neegongnaegong.domain.studygroup.MemberWeeklyStudyContentBySliceInfo
import com.ssafy.neegongnaegong.domain.studygroup.StudyLogByTagInfo
import com.ssafy.neegongnaegong.domain.studygroup.StudyMemberInfo
import kotlinx.coroutines.flow.Flow

interface NetworkStudyGroupDataSource {
    suspend fun getMemberStudyLogsByTag(request: StudyMemberInfo): Flow<List<StudyLogByTagInfo>>

    suspend fun getMemberStudyContents(request: MemberStudyContentsInfo): Flow<MemberWeeklyStudyContentBySliceInfo>
}
