package com.ssafy.neegongnaegong.module.di.usecase

import com.ssafy.neegongnaegong.domain.repository.StudiesRepository
import com.ssafy.neegongnaegong.domain.usecase.GetStudiesUseCase
import com.ssafy.neegongnaegong.domain.usecase.studies.CreateVoteUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StudiesUseCaseModule {
    @Provides
    @Singleton
    fun provideGetStudiesUseCase(studiesRepository: StudiesRepository): GetStudiesUseCase = GetStudiesUseCase(studiesRepository)

    @Provides
    @Singleton
    fun provideCreateVoteUseCase(studiesRepository: StudiesRepository): CreateVoteUseCase = CreateVoteUseCase(studiesRepository)
}
