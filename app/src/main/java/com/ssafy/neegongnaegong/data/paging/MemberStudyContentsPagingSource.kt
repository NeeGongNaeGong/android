package com.ssafy.neegongnaegong.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ssafy.neegongnaegong.data.datasource.network.NetworkStudyGroupDataSource
import com.ssafy.neegongnaegong.domain.model.studygroup.MemberStudyContentSliceKey
import com.ssafy.neegongnaegong.domain.model.studygroup.MemberStudyContentsInfo
import com.ssafy.neegongnaegong.domain.model.studygroup.StudyContentInfo

class MemberStudyContentsPagingSource(
    private val dataSource: NetworkStudyGroupDataSource,
    private val studyGroupId: Long,
    private val userId: Long,
) : PagingSource<MemberStudyContentSliceKey, StudyContentInfo>() {
    override suspend fun load(params: LoadParams<MemberStudyContentSliceKey>): LoadResult<MemberStudyContentSliceKey, StudyContentInfo> {
        return try {
            val cursor = params.key
            val request = MemberStudyContentsInfo(
                studyGroupId = studyGroupId,
                userId = userId,
                lastCursorCreatedAt = cursor?.lastCursorCreatedAt,
                lastLearningRecordId = cursor?.lastLearningRecordId,
            )
            val response = dataSource.getMemberStudyContents(request).getOrThrow().data
            val data = response.content
            LoadResult.Page(
                data = data.map { it.toStudyContentInfo() },
                prevKey = null,
                nextKey = if (response.hasNext) {
                    MemberStudyContentSliceKey(response.cursorCreatedAt, response.cursorId)
                } else {
                    null
                }
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<MemberStudyContentSliceKey, StudyContentInfo>): MemberStudyContentSliceKey? {
        return state.anchorPosition?.let { state.closestPageToPosition(it)?.nextKey }
    }
}
