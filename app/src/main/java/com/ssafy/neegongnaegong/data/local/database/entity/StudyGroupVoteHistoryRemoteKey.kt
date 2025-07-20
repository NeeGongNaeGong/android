package com.ssafy.neegongnaegong.data.local.database.entity

import androidx.room.Entity
import java.time.LocalDateTime

@Entity("study_group_vote_history_remote_key", primaryKeys = ["studyGroupId", "cursorId", "cursorTime"])
data class StudyGroupVoteHistoryRemoteKey(
    val studyGroupId: Long,
    val cursorTime: LocalDateTime,
    val cursorId: Long,
)
