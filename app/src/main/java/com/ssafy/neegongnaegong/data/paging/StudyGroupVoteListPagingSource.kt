package com.ssafy.neegongnaegong.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ssafy.neegongnaegong.data.datasource.network.NetworkStudyGroupDataSource
import com.ssafy.neegongnaegong.data.mapper.studygroup.StudyGroupVoteHistoryInfoMapper.toDomain
import com.ssafy.neegongnaegong.data.model.studygroup.response.StudyGroupVoteHistoryResponse
import com.ssafy.neegongnaegong.domain.model.studygroup.CursorSliceKey
import com.ssafy.neegongnaegong.domain.model.studygroup.StudyGroupVoteListRequest
import com.ssafy.neegongnaegong.domain.model.studygroup.VoteHistoryInfo
import kotlinx.coroutines.flow.first

class StudyGroupVoteListPagingSource(
    private val dataSource: NetworkStudyGroupDataSource,
    private val studyGroupId: Long,
) : PagingSource<CursorSliceKey, VoteHistoryInfo>() {
    override suspend fun load(params: LoadParams<CursorSliceKey>): LoadResult<CursorSliceKey, VoteHistoryInfo> {
        return try {
            val cursor = params.key
            val request =
                StudyGroupVoteListRequest(
                    studyGroupId = studyGroupId,
                    cursorTime = cursor?.cursorCreatedAt,
                    cursorId = cursor?.cursorId,
                )
            val response = dataSource.getStudyGroupVoteList(request).first()
            val data: List<StudyGroupVoteHistoryResponse> = response.content
            LoadResult.Page(
                data = data.toDomain(),
                prevKey = null,
                nextKey =
                    if (response.hasNext) {
                        CursorSliceKey(response.cursorCreatedAt, response.cursorId)
                    } else {
                        null
                    },
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<CursorSliceKey, VoteHistoryInfo>): CursorSliceKey? {
        return null
    }
}
