package com.ssafy.neegongnaegong.data.local.database.module

import android.content.Context
import androidx.room.Room
import com.ssafy.neegongnaegong.data.local.database.NeeGongNaeGongDatabase
import com.ssafy.neegongnaegong.data.local.database.dao.NotificationDao
import com.ssafy.neegongnaegong.data.local.database.dao.NotificationRemoteKeyDao
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
        @ApplicationContext context: Context
    ): NeeGongNaeGongDatabase = Room
        .databaseBuilder(
            context = context,
            klass = NeeGongNaeGongDatabase::class.java,
            name = "neegongnaegong.db"
        ).build()


    @Singleton
    @Provides
    fun providesNotificationDao(neeGongNaeGongDatabase: NeeGongNaeGongDatabase): NotificationDao =
        neeGongNaeGongDatabase.notificationDao()

    @Singleton
    @Provides
    fun providesNotificationRemoteKeyDao(neeGongNaeGongDatabase: NeeGongNaeGongDatabase): NotificationRemoteKeyDao =
        neeGongNaeGongDatabase.notificationRemoteKeyDao()

}
