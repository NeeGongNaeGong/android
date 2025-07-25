package com.ssafy.neegongnaegong.data.local.database.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class MigrationFrom3To4 : Migration(3, 4) {
    /**
     * 이번 마이그레이션에서는 커서 관련 response가 바뀜에 따라 Notification RemoteKey 테이블의 컬럼 속성이 변경되었습니다.
     * NotificationRemoteKeyEntity의 Key 속성 변경
     * 기존 : id: String, nextCursor: Long?
     * 수정 : id: String, nextCursor: NextCursorData?(embedded)
     *
     * 테이블 구조가 바뀌어 어쩔 수 없이 그냥 테이블을 삭제 후 재 생성하는 것으로 결정하였습니다
     */
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("DROP TABLE IF EXISTS notification_remote_keys")

        database.execSQL(
            """
            CREATE TABLE `notification_remote_keys` (
                `id` INTEGER NOT NULL, 
                `cursorValue` TEXT, 
                `cursorId` INTEGER, 
                `first` INTEGER, 
                PRIMARY KEY(`id`)
            )
        """,
        )
    }
}
