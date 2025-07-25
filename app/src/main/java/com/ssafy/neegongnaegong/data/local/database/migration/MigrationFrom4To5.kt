package com.ssafy.neegongnaegong.data.local.database.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class MigrationFrom4To5 : Migration(4, 5) {
    /**
     *
     * 이번 마이그레이션에서는 커서 관련 response가 바뀜에 따라 VoteHistory RemoteKey 테이블의 컬럼 속성이 변경되었습니다.
     * StudyGroupVoteHistoryRemoteKey의 Key 속성 변경
     * 테이블 구조가 바뀌어 어쩔 수 없이 그냥 테이블을 삭제 후 재 생성하는 것으로 결정하였습니다
     */
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("DROP TABLE IF EXISTS study_group_vote_history_remote_key")

        database.execSQL(
            """
            CREATE TABLE `study_group_vote_history_remote_key` (
                `studyGroupId` INTEGER NOT NULL, 
                `cursorValue` TEXT NOT NULL, 
                `cursorId` INTEGER NOT NULL, 
                `first` INTEGER NOT NULL, 
                PRIMARY KEY(`studyGroupId`, `cursorValue`, `cursorId`, `first`)
            )
            """,
        )
    }
}
