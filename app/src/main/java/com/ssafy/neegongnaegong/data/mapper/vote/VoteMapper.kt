package com.ssafy.neegongnaegong.data.mapper.vote

import com.ssafy.neegongnaegong.data.model.studies.request.CreateVoteRequest
import com.ssafy.neegongnaegong.domain.model.studies.VoteInfo

internal object VoteMapper {
    fun VoteInfo.toCreateRequest() =
        CreateVoteRequest(
            title = title,
            startTime = startTime,
            endTime = endTime,
            state = state,
            items = items,
            isMultiple = multiple,
            isSecret = secret,
            isNotify = notify,
            isChoose = choose,
        )
}
