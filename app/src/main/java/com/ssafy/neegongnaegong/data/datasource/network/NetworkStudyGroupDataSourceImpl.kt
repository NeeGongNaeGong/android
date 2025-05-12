package com.ssafy.neegongnaegong.data.datasource.network

import com.ssafy.neegongnaegong.data.model.ApiResponse
import com.ssafy.neegongnaegong.data.model.apiFlow
import com.ssafy.neegongnaegong.data.remote.StudyGroupApi
import com.ssafy.neegongnaegong.domain.model.studygroup.MemberStudyContentsInfo
import com.ssafy.neegongnaegong.domain.model.studygroup.MemberWeeklyStudyContentBySliceInfo
import com.ssafy.neegongnaegong.domain.model.studygroup.StudyLogByTagInfo
import com.ssafy.neegongnaegong.domain.model.studygroup.StudyMemberInfo
import kotlinx.collections.immutable.PersistentList
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NetworkStudyGroupDataSourceImpl @Inject constructor(
    private val api: StudyGroupApi
) : NetworkStudyGroupDataSource {
    override fun getMemberStudyLogsByTag(request: StudyMemberInfo): Flow<PersistentList<StudyLogByTagInfo>> =
        apiFlow {
            api.getMemberStudyLogsByTag(request.studyGroupId, request.targetUserId)
        }

    override suspend fun getMemberStudyContents(request: MemberStudyContentsInfo): Result<ApiResponse<MemberWeeklyStudyContentBySliceInfo>> =
            api.getMemberStudyContents(
                request.studyGroupId,
                request.userId,
                request.lastCursorCreatedAt,
                request.lastLearningRecordId,
                request.size
            )

}
