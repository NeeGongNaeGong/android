package com.ssafy.neegongnaegong.data.repository

import com.ssafy.neegongnaegong.data.datasource.network.NetworkStudiesDataSource
import com.ssafy.neegongnaegong.data.model.studies.request.CreateVoteRequest
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

    override suspend fun createVote(studyId: Int, voteInfo: VoteInfo): Flow<Unit> =
        dataSource.createVote(studyId, CreateVoteRequest.fromDomain(voteInfo))

}
