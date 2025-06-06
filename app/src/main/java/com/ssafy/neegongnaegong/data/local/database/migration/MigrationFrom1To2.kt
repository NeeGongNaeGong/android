package com.ssafy.neegongnaegong.data.local.database.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object MigrationFrom1To2 : Migration(1, 2) {

    /**
     * 이번 마이그레이션에서는 실제 스키마 변경이 없습니다.
     *
     * Kotlin의 enum 클래스 이름만 변경되었고
     * (`NotificationType` → `NotificationLocalType`),
     * 이는 DB 테이블 구조에 영향을 주지 않기 때문에
     * 마이그레이션 로직은 비워둡니다.
     */
    override fun migrate(db: SupportSQLiteDatabase) = Unit
}
