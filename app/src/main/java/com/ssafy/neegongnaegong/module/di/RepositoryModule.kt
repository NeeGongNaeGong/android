package com.ssafy.neegongnaegong.module.di

import com.ssafy.neegongnaegong.data.repository.CalendarRepositoryImpl
import com.ssafy.neegongnaegong.data.repository.GitHubRepositoryImpl
import com.ssafy.neegongnaegong.domain.repository.CalendarRepository
import com.ssafy.neegongnaegong.domain.repository.GitHubRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Singleton
    @Binds
    fun bindGitHubRepository(
        gitHubRepositoryImpl: GitHubRepositoryImpl
    ): GitHubRepository

    @Singleton
    @Binds
    fun bindCalendarRepository(
        calendarRepositoryImpl: CalendarRepositoryImpl
    ): CalendarRepository
}
