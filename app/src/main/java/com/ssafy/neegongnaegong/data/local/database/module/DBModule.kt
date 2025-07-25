package com.ssafy.neegongnaegong.data.local.database.module

import android.content.Context
import androidx.room.Room
import com.ssafy.neegongnaegong.data.local.database.NeeGongNaeGongDatabase
import com.ssafy.neegongnaegong.data.local.database.dao.LocalNotificationDataSource
import com.ssafy.neegongnaegong.data.local.database.dao.LocalNotificationRemoteKeyDataSource
import com.ssafy.neegongnaegong.data.local.database.migration.MigrationFrom1To2
import com.ssafy.neegongnaegong.data.local.database.migration.MigrationFrom3To4
import com.ssafy.neegongnaegong.data.local.database.migration.MigrationFrom4To5
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DBModule {
    @Singleton
    @Provides
    fun providesDatabase(
        @ApplicationContext context: Context,
    ): NeeGongNaeGongDatabase =
        Room
            .databaseBuilder(
                context = context,
                klass = NeeGongNaeGongDatabase::class.java,
                name = "neegongnaegong.db",
            )
            .addMigrations(MigrationFrom1To2(), MigrationFrom3To4(), MigrationFrom4To5())
            .build()

    @Singleton
    @Provides
    fun providesLocalNotificationDataSource(neeGongNaeGongDatabase: NeeGongNaeGongDatabase): LocalNotificationDataSource =
        neeGongNaeGongDatabase.localNotificationDataSource()

    @Singleton
    @Provides
    fun providesLocalNotificationRemoteKeyDataSource(neeGongNaeGongDatabase: NeeGongNaeGongDatabase): LocalNotificationRemoteKeyDataSource =
        neeGongNaeGongDatabase.localNotificationRemoteKeyDataSource()
}
