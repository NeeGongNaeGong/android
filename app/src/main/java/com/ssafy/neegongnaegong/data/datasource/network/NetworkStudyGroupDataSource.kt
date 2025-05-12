package com.ssafy.neegongnaegong.data.datasource.network

import com.ssafy.neegongnaegong.domain.model.studygroup.MemberStudyContentsInfo
import com.ssafy.neegongnaegong.domain.model.studygroup.MemberWeeklyStudyContentBySliceInfo
import com.ssafy.neegongnaegong.domain.model.studygroup.StudyLogByTagInfo
import com.ssafy.neegongnaegong.domain.model.studygroup.StudyMemberInfo
import kotlinx.coroutines.flow.Flow

interface NetworkStudyGroupDataSource {
    suspend fun getMemberStudyLogsByTag(request: StudyMemberInfo): Flow<List<StudyLogByTagInfo>>

    suspend fun getMemberStudyContents(request: MemberStudyContentsInfo): Flow<MemberWeeklyStudyContentBySliceInfo>
}
