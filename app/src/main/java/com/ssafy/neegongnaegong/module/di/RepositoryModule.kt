package com.ssafy.neegongnaegong.module.di

import com.ssafy.neegongnaegong.data.repository.AuthRepositoryImpl
import com.ssafy.neegongnaegong.data.repository.CalendarRepositoryImpl
import com.ssafy.neegongnaegong.data.repository.CategoryRepositoryImpl
import com.ssafy.neegongnaegong.data.repository.FileRepositoryImpl
import com.ssafy.neegongnaegong.data.repository.LearningRecordRepositoryImpl
import com.ssafy.neegongnaegong.data.repository.LocalFileRepositoryImpl
import com.ssafy.neegongnaegong.data.repository.NotificationRepositoryImpl
import com.ssafy.neegongnaegong.data.repository.S3RepositoryImpl
import com.ssafy.neegongnaegong.data.repository.StudiesRepositoryImpl
import com.ssafy.neegongnaegong.data.repository.StudyGroupRepositoryImpl
import com.ssafy.neegongnaegong.data.repository.UserRepositoryImpl
import com.ssafy.neegongnaegong.domain.repository.AuthRepository
import com.ssafy.neegongnaegong.domain.repository.CalendarRepository
import com.ssafy.neegongnaegong.domain.repository.CategoryRepository
import com.ssafy.neegongnaegong.domain.repository.FileRepository
import com.ssafy.neegongnaegong.domain.repository.LearningRecordRepository
import com.ssafy.neegongnaegong.domain.repository.LocalFileRepository
import com.ssafy.neegongnaegong.domain.repository.NotificationRepository
import com.ssafy.neegongnaegong.domain.repository.S3Repository
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
    fun bindCategoryRepository(categoryRepositoryImpl: CategoryRepositoryImpl): CategoryRepository

    @Binds
    @Singleton
    fun bindCalendarRepository(calendarRepositoryImpl: CalendarRepositoryImpl): CalendarRepository

    @Binds
    @Singleton
    fun bindStudyGroupRepository(studyGroupRepositoryImpl: StudyGroupRepositoryImpl): StudyGroupRepository

    @Binds
    @Singleton
    fun bindLearningRepository(learningRecordRepositoryImpl: LearningRecordRepositoryImpl): LearningRecordRepository

    @Binds
    @Singleton
    fun bindFileRepository(fileRepositoryImpl: FileRepositoryImpl): FileRepository

    @Binds
    @Singleton
    fun bindS3Repository(s3RepositoryImpl: S3RepositoryImpl): S3Repository

    @Binds
    @Singleton
    fun bindNotificationRepository(notificationRepositoryImpl: NotificationRepositoryImpl): NotificationRepository

    @Binds
    @Singleton
    fun bindLocalFileRepository(localFileRepositoryImpl: LocalFileRepositoryImpl): LocalFileRepository
}
