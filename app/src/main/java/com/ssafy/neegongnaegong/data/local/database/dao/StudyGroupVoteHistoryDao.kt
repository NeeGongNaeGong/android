package com.ssafy.neegongnaegong.data.local.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ssafy.neegongnaegong.data.local.database.entity.StudyGroupVoteHistory

@Dao
interface StudyGroupVoteHistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertVoteHistory(voteHistory: List<StudyGroupVoteHistory>)

    @Query("SELECT * FROM study_group_vote_history WHERE studyGroupId = :studyGroupId ORDER BY id DESC")
    fun getAll(studyGroupId: Long): PagingSource<Int, StudyGroupVoteHistory>

    @Query("DELETE FROM study_group_vote_history WHERE studyGroupId = :studyGroupId")
    fun deleteVoteHistory(studyGroupId: Long)
}
