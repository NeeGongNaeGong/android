package com.ssafy.neegongnaegong.module.di

import com.ssafy.neegongnaegong.data.datasource.CalendarDataSource
import com.ssafy.neegongnaegong.data.datasource.CalendarDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {
    @Singleton
    @Binds
    abstract fun bindCalendarDataSource(calendarDataSourceImpl: CalendarDataSourceImpl): CalendarDataSource
}