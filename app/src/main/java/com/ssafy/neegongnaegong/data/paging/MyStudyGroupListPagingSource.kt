package com.ssafy.neegongnaegong.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ssafy.neegongnaegong.data.datasource.network.NetworkStudyGroupDataSource
import com.ssafy.neegongnaegong.data.mapper.studygroup.MyStudyGroupListMapper.toDomain
import com.ssafy.neegongnaegong.data.mapper.studygroup.StudyGroupVoteHistoryInfoMapper.toDomain
import com.ssafy.neegongnaegong.data.model.studygroup.response.MyStudyGroupListResponse
import com.ssafy.neegongnaegong.domain.model.studygroup.CursorSliceKey
import com.ssafy.neegongnaegong.domain.model.studygroup.MyStudyGroupInfo
import kotlinx.coroutines.flow.first

class MyStudyGroupListPagingSource(
    private val dataSource: NetworkStudyGroupDataSource,
) : PagingSource<CursorSliceKey, MyStudyGroupInfo>() {
    override suspend fun load(params: LoadParams<CursorSliceKey>): LoadResult<CursorSliceKey, MyStudyGroupInfo> {
        return try {
            val cursor = params.key

            val response =
                with(cursor) {
                    dataSource.getMyStudyGroupList(
                        this?.cursorCreatedAt,
                        this?.cursorId,
                        10,
                    ).first()
                }

            val data: List<MyStudyGroupListResponse.MyStudyGroupResponse> = response.content
            LoadResult.Page(
                data = data.map { it.toDomain() },
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

    override fun getRefreshKey(state: PagingState<CursorSliceKey, MyStudyGroupInfo>): CursorSliceKey? {
        return null
    }
}
