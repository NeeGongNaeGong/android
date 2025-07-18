package com.ssafy.neegongnaegong.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ssafy.neegongnaegong.data.local.database.entity.StudyGroupVoteHistoryRemoteKey

@Dao
interface StudyGroupVoteHistoryRemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(remoteKey: StudyGroupVoteHistoryRemoteKey)

    @Query("SELECT * FROM study_group_vote_history_remote_key WHERE studyGroupId = :studyGroupId ORDER BY cursorId DESC LIMIT 1")
    suspend fun getNextKey(studyGroupId: Long): StudyGroupVoteHistoryRemoteKey

    @Query("DELETE FROM study_group_vote_history_remote_key WHERE studyGroupId = :studyGroupId")
    suspend fun deleteStudyGroupIdKey(studyGroupId: Long)
}
