package com.ssafy.neegongnaegong.module.di

import com.ssafy.neegongnaegong.data.datasource.local.LocalFcmDataSource
import com.ssafy.neegongnaegong.data.datasource.local.LocalFcmDataSourceImpl
import com.ssafy.neegongnaegong.data.datasource.local.LocalFileDataSource
import com.ssafy.neegongnaegong.data.datasource.local.LocalFileDataSourceImpl
import com.ssafy.neegongnaegong.data.datasource.local.LocalUpdateDataSource
import com.ssafy.neegongnaegong.data.datasource.local.LocalUpdateDataSourceImpl
import com.ssafy.neegongnaegong.data.datasource.local.LocalUserDataSource
import com.ssafy.neegongnaegong.data.datasource.local.LocalUserDataSourceImpl
import com.ssafy.neegongnaegong.data.datasource.network.NetworkAuthDataSource
import com.ssafy.neegongnaegong.data.datasource.network.NetworkAuthDataSourceImpl
import com.ssafy.neegongnaegong.data.datasource.network.NetworkCalendarDataSource
import com.ssafy.neegongnaegong.data.datasource.network.NetworkCalendarDataSourceImpl
import com.ssafy.neegongnaegong.data.datasource.network.NetworkCategoryDataSource
import com.ssafy.neegongnaegong.data.datasource.network.NetworkCategoryDataSourceImpl
import com.ssafy.neegongnaegong.data.datasource.network.NetworkFileDataSource
import com.ssafy.neegongnaegong.data.datasource.network.NetworkFileDataSourceImpl
import com.ssafy.neegongnaegong.data.datasource.network.NetworkLearningRecordDataSource
import com.ssafy.neegongnaegong.data.datasource.network.NetworkLearningRecordDataSourceImpl
import com.ssafy.neegongnaegong.data.datasource.network.NetworkNotificationDataSource
import com.ssafy.neegongnaegong.data.datasource.network.NetworkNotificationDataSourceImpl
import com.ssafy.neegongnaegong.data.datasource.network.NetworkS3DataSource
import com.ssafy.neegongnaegong.data.datasource.network.NetworkS3DataSourceImpl
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

    @Singleton
    @Binds
    fun bindNetworkLearningRecordDataSource(
        networkLearningRecordDataSourceImpl: NetworkLearningRecordDataSourceImpl,
    ): NetworkLearningRecordDataSource

    @Singleton
    @Binds
    fun bindNetworkStudiesDataSource(networkStudiesDataSourceImpl: NetworkStudiesDataSourceImpl): NetworkStudiesDataSource

    @Singleton
    @Binds
    fun bindNetworkCategoryDataSource(networkCategoryDataSourceImpl: NetworkCategoryDataSourceImpl): NetworkCategoryDataSource

    @Singleton
    @Binds
    fun bindNetworkFileDataSource(networkFileDataSourceImpl: NetworkFileDataSourceImpl): NetworkFileDataSource

    @Singleton
    @Binds
    fun bindNetworkS3DataSource(networkS3DataSourceImpl: NetworkS3DataSourceImpl): NetworkS3DataSource

    @Singleton
    @Binds
    fun bindNetworkNotificationDataSource(
        networkNotificationDataSourceImpl: NetworkNotificationDataSourceImpl,
    ): NetworkNotificationDataSource

    @Singleton
    @Binds
    fun bindLocalFileDataSource(localFileDataSourceImpl: LocalFileDataSourceImpl): LocalFileDataSource

    @Singleton
    @Binds
    fun bindLocalUpdateDataSource(localUpdateDataSourceImpl: LocalUpdateDataSourceImpl): LocalUpdateDataSource
}
