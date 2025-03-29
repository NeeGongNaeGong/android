package com.ssafy.neegongnaegong.module.di

import com.ssafy.neegongnaegong.data.datasource.network.NetworkAuthDataSource
import com.ssafy.neegongnaegong.data.datasource.network.NetworkAuthDataSourceImpl
import com.ssafy.neegongnaegong.data.datasource.network.NetworkCalendarDataSource
import com.ssafy.neegongnaegong.data.datasource.network.NetworkCalendarDataSourceImpl
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
}