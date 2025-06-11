package com.ssafy.neegongnaegong.data.datasource.network

import com.ssafy.neegongnaegong.data.model.ApiResponse
import com.ssafy.neegongnaegong.data.model.apiFlow
import com.ssafy.neegongnaegong.data.model.studygroup.response.MemberWeeklyStudyContentBySliceResponse
import com.ssafy.neegongnaegong.data.model.studygroup.response.StudyLogByTagResponse
import com.ssafy.neegongnaegong.data.remote.StudyGroupApi
import com.ssafy.neegongnaegong.domain.model.studygroup.MemberStudyContentsInfo
import com.ssafy.neegongnaegong.domain.model.studygroup.StudyMemberInfo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NetworkStudyGroupDataSourceImpl
    @Inject
    constructor(
        private val api: StudyGroupApi,
    ) : NetworkStudyGroupDataSource {
        override fun getMemberStudyLogsByTag(request: StudyMemberInfo): Flow<List<StudyLogByTagResponse>> =
            apiFlow {
                api.getMemberStudyLogsByTag(request.studyGroupId, request.targetUserId)
            }

        override suspend fun getMemberStudyContents(
            request: MemberStudyContentsInfo,
        ): Result<ApiResponse<MemberWeeklyStudyContentBySliceResponse>> =
            api.getMemberStudyContents(
                request.studyGroupId,
                request.userId,
                request.cursorCreatedAt,
                request.cursorId,
                request.size,
            )

        override fun approveStudyGroupJoin(
            studyGroupId: Long,
            userId: Long,
            notificationId: Long?,
        ): Flow<Unit> =
            apiFlow {
                api.approveStudyGroupJoin(
                    studyGroupId = studyGroupId,
                    userId = userId,
                    notificationId = notificationId,
                )
            }

        override fun rejectStudyGroupJoin(
            studyGroupId: Long,
            userId: Long,
            notificationId: Long?,
        ): Flow<Unit> =
            apiFlow {
                api.rejectStudyGroupJoin(
                    studyGroupId = studyGroupId,
                    userId = userId,
                    notificationId = notificationId,
                )
            }
    }
