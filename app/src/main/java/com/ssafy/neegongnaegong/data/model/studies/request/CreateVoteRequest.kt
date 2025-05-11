package com.ssafy.neegongnaegong.data.model.studies.request

import com.ssafy.neegongnaegong.domain.model.studies.VoteInfo
import java.time.LocalDateTime

data class CreateVoteRequest(
    val title: String,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime?,
    val state: Boolean = true,
    val items: List<String>,
    val multiple: Boolean,
    val secret: Boolean,
    val notify: Boolean
) {
    companion object{
        fun fromDomain(voteInfo: VoteInfo): CreateVoteRequest =
            CreateVoteRequest(
                voteInfo.title,
                voteInfo.startTime,
                voteInfo.endTime,
                voteInfo.state,
                voteInfo.items,
                voteInfo.multiple,
                voteInfo.secret,
                voteInfo.notify
            )
    }

}
