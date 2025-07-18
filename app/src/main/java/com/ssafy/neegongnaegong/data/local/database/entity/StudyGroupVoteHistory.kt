package com.ssafy.neegongnaegong.data.local.database.entity

import androidx.room.Entity
import java.time.LocalDateTime

@Entity(tableName = "study_group_vote_history", primaryKeys = ["studyGroupId", "id"])
data class StudyGroupVoteHistory(
    val studyGroupId: Long,
    val id: Long,
    val title: String,
    val endTime: LocalDateTime?,
    val participationMember: Int,
    val voted: Boolean,
)
