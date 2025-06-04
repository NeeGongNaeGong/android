package com.ssafy.neegongnaegong.data.datasource.network

import com.ssafy.neegongnaegong.data.model.ApiResponse
import com.ssafy.neegongnaegong.data.model.studygroup.response.MemberWeeklyStudyContentBySliceResponse
import com.ssafy.neegongnaegong.data.model.studygroup.response.StudyGroupNoticeListBySliceResponse
import com.ssafy.neegongnaegong.data.model.studygroup.response.StudyGroupVoteListBySliceResponse
import com.ssafy.neegongnaegong.data.model.studygroup.response.StudyLogByTagResponse
import com.ssafy.neegongnaegong.domain.model.studygroup.MemberStudyContentsInfo
import com.ssafy.neegongnaegong.domain.model.studygroup.StudyGroupVoteListInfo
import com.ssafy.neegongnaegong.domain.model.studygroup.StudyMemberInfo
import kotlinx.coroutines.flow.Flow

interface NetworkStudyGroupDataSource {
    fun getMemberStudyLogsByTag(request: StudyMemberInfo): Flow<List<StudyLogByTagResponse>>

    suspend fun getMemberStudyContents(request: MemberStudyContentsInfo): Result<ApiResponse<MemberWeeklyStudyContentBySliceResponse>>

    suspend fun getStudyGroupVoteList(request: StudyGroupVoteListInfo): Result<ApiResponse<StudyGroupVoteListBySliceResponse>>

    suspend fun getStudyGroupNoticeList(request: StudyGroupVoteListInfo): Result<ApiResponse<StudyGroupNoticeListBySliceResponse>>
}
