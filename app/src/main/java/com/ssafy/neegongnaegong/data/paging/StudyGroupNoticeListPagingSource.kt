package com.ssafy.neegongnaegong.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ssafy.neegongnaegong.data.datasource.network.NetworkStudyGroupDataSource
import com.ssafy.neegongnaegong.data.mapper.studygroup.StudyGroupNoticeHistoryInfoMapper.toDomain
import com.ssafy.neegongnaegong.data.mapper.studygroup.StudyGroupVoteHistoryInfoMapper.toDomain
import com.ssafy.neegongnaegong.data.model.studygroup.response.StudyGroupNoticeHistoryResponse
import com.ssafy.neegongnaegong.domain.model.studygroup.NoticeHistoryInfo
import com.ssafy.neegongnaegong.domain.model.studygroup.StudyGroupNoticeListInfo

class StudyGroupNoticeListPagingSource(
    private val dataSource: NetworkStudyGroupDataSource,
    private val studyGroupId: Long,
) : PagingSource<Long, NoticeHistoryInfo>() {
    override suspend fun load(params: LoadParams<Long>): LoadResult<Long, NoticeHistoryInfo> {
        return try {
            val cursor = params.key
            val request =
                StudyGroupNoticeListInfo(
                    studyGroupId = studyGroupId,
                    cursorId = cursor,
                    size = 10,
                )
            val response = dataSource.getStudyGroupNoticeList(request).getOrThrow().data
            val data: List<StudyGroupNoticeHistoryResponse> = response.content
            LoadResult.Page(
                data = data.toDomain(),
                prevKey = null,
                nextKey =
                    if (response.hasNext) {
                        response.cursorId
                    } else {
                        null
                    },
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Long, NoticeHistoryInfo>): Long? {
        return null
    }
}
