package com.ssafy.neegongnaegong.module.di

import com.ssafy.neegongnaegong.data.datasource.local.LocalFcmDataSource
import com.ssafy.neegongnaegong.data.datasource.local.LocalFcmDataSourceImpl
import com.ssafy.neegongnaegong.data.datasource.local.LocalUserDataSource
import com.ssafy.neegongnaegong.data.datasource.local.LocalUserDataSourceImpl
import com.ssafy.neegongnaegong.data.datasource.network.NetworkAuthDataSource
import com.ssafy.neegongnaegong.data.datasource.network.NetworkAuthDataSourceImpl
import com.ssafy.neegongnaegong.data.datasource.network.NetworkCalendarDataSource
import com.ssafy.neegongnaegong.data.datasource.network.NetworkCalendarDataSourceImpl
import com.ssafy.neegongnaegong.data.datasource.network.NetworkLearningRecordDataSource
import com.ssafy.neegongnaegong.data.datasource.network.NetworkLearningRecordDataSourceImpl
import com.ssafy.neegongnaegong.data.datasource.network.NetworkStudiesDataSource
import com.ssafy.neegongnaegong.data.datasource.network.NetworkStudiesDataSourceImpl
import com.ssafy.neegongnaegong.data.datasource.network.NetworkStudyGroupDataSource
import com.ssafy.neegongnaegong.data.datasource.network.NetworkStudyGroupDataSourceImpl
import com.ssafy.neegongnaegong.data.datasource.network.NetworkUserDataSource
import com.ssafy.neegongnaegong.data.datasource.network.NetworkUserDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface DataSourceModule {
    @Singleton
    @Binds
    fun bindCalendarDataSource(calendarDataSourceImpl: NetworkCalendarDataSourceImpl): NetworkCalendarDataSource

    @Singleton
    @Binds
    fun bindAuthDataSource(authDataSourceImpl: NetworkAuthDataSourceImpl): NetworkAuthDataSource

    @Singleton
    @Binds
    fun bindNetworkUserDataSource(networkUserDataSourceImpl: NetworkUserDataSourceImpl): NetworkUserDataSource

    @Singleton
    @Binds
    fun bindLocalUserDataSource(localUserDataSourceImpl: LocalUserDataSourceImpl): LocalUserDataSource

    @Singleton
    @Binds
    fun bindNetworkStudyGroupDataSource(networkStudyGroupDataSource: NetworkStudyGroupDataSourceImpl): NetworkStudyGroupDataSource

    @Binds
    fun bindLocalFcmDataSource(localFcmDataSourceImpl: LocalFcmDataSourceImpl): LocalFcmDataSource

    @Binds
    fun bindNetworkStudiesDataSource(networkStudiesDataSourceImpl: NetworkStudiesDataSourceImpl): NetworkStudiesDataSource

    @Singleton
    @Binds
    fun bindNetworkLearningRecordDataSource(
        networkLearningRecordDataSourceImpl: NetworkLearningRecordDataSourceImpl,
    ): NetworkLearningRecordDataSource
}
