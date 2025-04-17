package com.ssafy.neegongnaegong.module.di

import com.ssafy.neegongnaegong.data.repository.AuthRepositoryImpl
import com.ssafy.neegongnaegong.data.repository.CalendarRepositoryImpl
import com.ssafy.neegongnaegong.data.repository.GitHubRepositoryImpl
import com.ssafy.neegongnaegong.data.repository.StudiesRepositoryImpl
import com.ssafy.neegongnaegong.data.repository.UserRepositoryImpl
import com.ssafy.neegongnaegong.domain.repository.AuthRepository
import com.ssafy.neegongnaegong.domain.repository.CalendarRepository
import com.ssafy.neegongnaegong.domain.repository.GitHubRepository
import com.ssafy.neegongnaegong.domain.repository.StudiesRepository
import com.ssafy.neegongnaegong.domain.repository.UserRepository
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
    fun bindGitHubRepository(gitHubRepositoryImpl: GitHubRepositoryImpl): GitHubRepository

    @Singleton
    @Binds
    fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Singleton
    @Binds
    fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @Singleton
    @Binds
    fun bindStudiesRepository(studiesRepositoryImpl: StudiesRepositoryImpl): StudiesRepository

    @Singleton
    @Binds
    fun bindCalendarRepository(calendarRepositoryImpl: CalendarRepositoryImpl): CalendarRepository
}
