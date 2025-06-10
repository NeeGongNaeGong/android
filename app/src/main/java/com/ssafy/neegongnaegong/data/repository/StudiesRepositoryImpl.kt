package com.ssafy.neegongnaegong.data.repository

import com.ssafy.neegongnaegong.data.datasource.network.NetworkStudiesDataSource
import com.ssafy.neegongnaegong.data.mapper.vote.VoteMapper.toCreateRequest
import com.ssafy.neegongnaegong.data.model.studies.request.CreateNoticeRequest
import com.ssafy.neegongnaegong.domain.model.studies.Studies
import com.ssafy.neegongnaegong.domain.model.studies.VoteInfo
import com.ssafy.neegongnaegong.domain.repository.StudiesRepository
import com.ssafy.neegongnaegong.module.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class StudiesRepositoryImpl
    @Inject
    constructor(
        private val dataSource: NetworkStudiesDataSource,
        @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    ) : StudiesRepository {
        override suspend fun getStudies(): List<Studies> = TODO()

        override suspend fun createVote(
            studyGroupId: Long,
            voteInfo: VoteInfo,
        ): Flow<Unit> = dataSource.createVote(studyGroupId, voteInfo.toCreateRequest()).flowOn(ioDispatcher)

        override fun createNotice(
            studyGroupId: Long,
            title: String,
            content: String,
        ): Flow<Unit> =
            dataSource.createNotice(studyGroupId, CreateNoticeRequest(title, content))
                .flowOn(ioDispatcher)
    }
