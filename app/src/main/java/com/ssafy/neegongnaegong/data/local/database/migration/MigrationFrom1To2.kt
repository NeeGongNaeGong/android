package com.ssafy.neegongnaegong.data.local.database.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object MigrationFrom1To2 : Migration(1, 2) {

    /**
     * 이번 마이그레이션에서는 두 개의 컬럼이 새로 추가되었습니다.
     *
     * - studyGroupId (Long?) : 스터디 그룹의 ID
     * - studyGroupName (String?) : 스터디 그룹의 이름
     *
     * 두 컬럼 모두 nullable 타입이므로, 기존 데이터에는 NULL로 기본값이 설정됩니다.
     * 기존 테이블 구조는 그대로 유지되며, 데이터 손실 없이 컬럼만 추가됩니다.
     */
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE notifications ADD COLUMN studyGroupId INTEGER")
        database.execSQL("ALTER TABLE notifications ADD COLUMN studyGroupName TEXT")
    }
}
