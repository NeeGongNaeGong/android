package com.ssafy.neegongnaegong.module.di

import com.ssafy.neegongnaegong.data.datasource.NetworkAuthDataSource
import com.ssafy.neegongnaegong.data.datasource.NetworkAuthDataSourceImpl
import com.ssafy.neegongnaegong.data.datasource.NetworkCalendarDataSource
import com.ssafy.neegongnaegong.data.datasource.NetworkCalendarDataSourceImpl
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