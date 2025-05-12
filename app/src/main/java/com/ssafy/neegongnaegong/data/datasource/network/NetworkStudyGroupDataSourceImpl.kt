package com.ssafy.neegongnaegong.data.datasource.network

import com.ssafy.neegongnaegong.data.model.apiFlow
import com.ssafy.neegongnaegong.data.remote.StudyGroupsApi
import com.ssafy.neegongnaegong.domain.studygroup.MemberStudyContentsInfo
import com.ssafy.neegongnaegong.domain.studygroup.MemberWeeklyStudyContentBySliceInfo
import com.ssafy.neegongnaegong.domain.studygroup.StudyLogByTagInfo
import com.ssafy.neegongnaegong.domain.studygroup.StudyMemberInfo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NetworkStudyGroupDataSourceImpl @Inject constructor(
    private val api: StudyGroupsApi
) : NetworkStudyGroupDataSource {
    override suspend fun getMemberStudyLogsByTag(request: StudyMemberInfo): Flow<List<StudyLogByTagInfo>> =
        apiFlow {
            api.getMemberStudyLogsByTag(request.studyGroupId, request.targetUserId)
        }

    override suspend fun getMemberStudyContents(request: MemberStudyContentsInfo): Flow<MemberWeeklyStudyContentBySliceInfo> =
        apiFlow {
            api.getMemberStudyContents(
                request.studyGroupId,
                request.userId,
                request.lastCursorCreatedAt,
                request.lastLearningRecordId,
                request.size
            )
        }
}
