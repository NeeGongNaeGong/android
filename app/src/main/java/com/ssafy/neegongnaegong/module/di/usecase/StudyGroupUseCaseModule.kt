package com.ssafy.neegongnaegong.module.di.usecase

import com.ssafy.neegongnaegong.domain.repository.StudyGroupRepository
import com.ssafy.neegongnaegong.domain.usecase.studygroup.GetMemberStudyContentsUseCase
import com.ssafy.neegongnaegong.domain.usecase.studygroup.GetMemberStudyLogsByTagUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StudyGroupUseCaseModule {
    @Provides
    @Singleton
    fun provideGetMemberStudyContentsUseCase(repository: StudyGroupRepository): GetMemberStudyContentsUseCase =
        GetMemberStudyContentsUseCase(repository)

    @Provides
    @Singleton
    fun provideGetMemberStudyLogsByTagUseCase(repository: StudyGroupRepository): GetMemberStudyLogsByTagUseCase =
        GetMemberStudyLogsByTagUseCase(repository)

}
