package com.ssafy.neegongnaegong.module.di

import com.ssafy.neegongnaegong.data.repository.AuthRepositoryImpl
import com.ssafy.neegongnaegong.data.repository.CalendarRepositoryImpl
import com.ssafy.neegongnaegong.data.repository.StudiesRepositoryImpl
import com.ssafy.neegongnaegong.data.repository.StudyGroupRepositoryImpl
import com.ssafy.neegongnaegong.data.repository.UserRepositoryImpl
import com.ssafy.neegongnaegong.domain.repository.AuthRepository
import com.ssafy.neegongnaegong.domain.repository.CalendarRepository
import com.ssafy.neegongnaegong.domain.repository.StudiesRepository
import com.ssafy.neegongnaegong.domain.repository.StudyGroupRepository
import com.ssafy.neegongnaegong.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    @Singleton
    fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @Binds
    @Singleton
    fun bindStudiesRepository(studiesRepositoryImpl: StudiesRepositoryImpl): StudiesRepository

    @Binds
    @Singleton
    fun bindCalendarRepository(calendarRepositoryImpl: CalendarRepositoryImpl): CalendarRepository

    @Binds
    @Singleton
    fun bindStudyGroupRepository(studyGroupRepositoryImpl: StudyGroupRepositoryImpl): StudyGroupRepository
}
