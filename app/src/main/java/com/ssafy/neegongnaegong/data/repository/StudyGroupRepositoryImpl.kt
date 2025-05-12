package com.ssafy.neegongnaegong.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ssafy.neegongnaegong.data.datasource.network.NetworkStudyGroupDataSource
import com.ssafy.neegongnaegong.data.paging.MemberStudyContentsPagingSource
import com.ssafy.neegongnaegong.domain.model.studygroup.StudyContentInfo
import com.ssafy.neegongnaegong.domain.model.studygroup.StudyLogByTagInfo
import com.ssafy.neegongnaegong.domain.model.studygroup.StudyMemberInfo
import com.ssafy.neegongnaegong.domain.repository.StudyGroupRepository
import kotlinx.collections.immutable.PersistentList
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StudyGroupRepositoryImpl
@Inject
constructor(
    private val dataSource: NetworkStudyGroupDataSource
) : StudyGroupRepository {
    override fun getMemberStudyLogsByTag(request: StudyMemberInfo): Flow<PersistentList<StudyLogByTagInfo>> =
        dataSource.getMemberStudyLogsByTag(request)

    override fun getMemberStudyContents(request: StudyMemberInfo):
            Flow<PagingData<StudyContentInfo>> =
        Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = {
                MemberStudyContentsPagingSource(
                    dataSource,
                    request.studyGroupId,
                    request.targetUserId
                )
            }
        ).flow

}
