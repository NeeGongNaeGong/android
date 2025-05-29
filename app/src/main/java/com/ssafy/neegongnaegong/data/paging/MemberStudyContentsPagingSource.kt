package com.ssafy.neegongnaegong.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ssafy.neegongnaegong.data.datasource.network.NetworkStudyGroupDataSource
import com.ssafy.neegongnaegong.data.mapper.studygroup.StudyContentInfoMapper.toDomain
import com.ssafy.neegongnaegong.data.model.studygroup.response.StudyContentResponse
import com.ssafy.neegongnaegong.domain.model.studygroup.CursorSliceKey
import com.ssafy.neegongnaegong.domain.model.studygroup.MemberStudyContentsInfo
import com.ssafy.neegongnaegong.domain.model.studygroup.StudyContentInfo

class MemberStudyContentsPagingSource(
    private val dataSource: NetworkStudyGroupDataSource,
    private val studyGroupId: Long,
    private val userId: Long,
) : PagingSource<CursorSliceKey, StudyContentInfo>() {
    override suspend fun load(params: LoadParams<CursorSliceKey>): LoadResult<CursorSliceKey, StudyContentInfo> {
        return try {
            val cursor = params.key
            val request =
                MemberStudyContentsInfo(
                    studyGroupId = studyGroupId,
                    userId = userId,
                    cursorCreatedAt = cursor?.cursorCreatedAt,
                    cursorId = cursor?.cursorId,
                )
            val response = dataSource.getMemberStudyContents(request).getOrThrow().data
            val data: List<StudyContentResponse> = response.content
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

    override fun getRefreshKey(state: PagingState<CursorSliceKey, StudyContentInfo>): CursorSliceKey? {
        /**
         * 현재 보고있는 부분의 Page 데이터의 맨 앞의 키를 바탕으로 다시 갱신
         * 현재 보여지고 있는 부분의 데이터를 갱신하는데 가장 확실한 방법일 것 같아서
         * closestItem이 아니라 closestPage를 기준으로 적용
         * closestItem은 마지막으로 접근한 Index의 item을 반환한다고 되어 있어서 가장 마지막에 나온 아이템을 돌려줄 듯
         * 그래서 화면 상에서 보이는 데이터의 refresh를 하기엔 적합하지 않아보임
         */
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.let { page ->
                page.data.first().let { item ->
                    item.let {
                        CursorSliceKey(it.cursorCreatedAt, it.cursorId)
                    }
                }
            }
        }
    }
}
