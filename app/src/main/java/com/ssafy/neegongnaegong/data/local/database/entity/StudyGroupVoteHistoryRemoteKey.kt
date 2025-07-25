package com.ssafy.neegongnaegong.data.local.database.entity

import androidx.room.Embedded
import androidx.room.Entity
import com.ssafy.neegongnaegong.data.model.cursor.NextCursorData

@Entity("study_group_vote_history_remote_key", primaryKeys = ["studyGroupId", "cursorValue", "cursorId", "first"])
data class StudyGroupVoteHistoryRemoteKey(
    val studyGroupId: Long,
    @Embedded
    val nextCursor: NextCursorData,
)
