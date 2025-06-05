package com.ssafy.neegongnaegong.domain.repository

import com.ssafy.neegongnaegong.domain.model.studies.Studies
import com.ssafy.neegongnaegong.domain.model.studies.VoteInfo
import kotlinx.coroutines.flow.Flow

interface StudiesRepository {
    suspend fun getStudies(): List<Studies>

    suspend fun createVote(
        studyId: Int,
        voteInfo: VoteInfo,
    ): Flow<Unit>

    fun createNotice(
        studyGroupId: Long,
        title: String,
        content: String,
    ): Flow<Unit>
}
