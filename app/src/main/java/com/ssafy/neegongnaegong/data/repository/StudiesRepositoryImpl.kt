package com.ssafy.neegongnaegong.data.repository

import com.ssafy.neegongnaegong.data.datasource.network.NetworkStudiesDataSource
import com.ssafy.neegongnaegong.data.mapper.vote.VoteMapper.toCreateRequest
import com.ssafy.neegongnaegong.data.model.studies.request.CreateNoticeRequest
import com.ssafy.neegongnaegong.domain.model.studies.Studies
import com.ssafy.neegongnaegong.domain.model.studies.VoteInfo
import com.ssafy.neegongnaegong.domain.repository.StudiesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StudiesRepositoryImpl
    @Inject
    constructor(
        private val dataSource: NetworkStudiesDataSource,
    ) : StudiesRepository {
        override suspend fun getStudies(): List<Studies> = TODO()

        override suspend fun createVote(
            studyId: Int,
            voteInfo: VoteInfo,
        ): Flow<Unit> = dataSource.createVote(studyId, voteInfo.toCreateRequest())

        override fun createNotice(
            studyGroupId: Long,
            title: String,
            content: String,
        ): Flow<Unit> = dataSource.createNotice(studyGroupId, CreateNoticeRequest(title, content))
    }
