package com.ssafy.neegongnaegong.module.di.usecase

import com.ssafy.neegongnaegong.domain.repository.StudiesRepository
import com.ssafy.neegongnaegong.domain.usecase.GetStudiesUseCase
import com.ssafy.neegongnaegong.domain.usecase.studies.ApplyStudiesUseCase
import com.ssafy.neegongnaegong.domain.usecase.studies.CancelApplicationsStudiesUseCase
import com.ssafy.neegongnaegong.domain.usecase.studies.CreateStudiesUseCase
import com.ssafy.neegongnaegong.domain.usecase.studies.CreateVoteUseCase
import com.ssafy.neegongnaegong.domain.usecase.studies.DeleteStudiesUseCase
import com.ssafy.neegongnaegong.domain.usecase.studies.GetStudiesDetailUseCase
import com.ssafy.neegongnaegong.domain.usecase.studies.GetStudiesListUseCase
import com.ssafy.neegongnaegong.domain.usecase.studies.GetStudiesMembersUseCase
import com.ssafy.neegongnaegong.domain.usecase.studies.UpdateStudiesUseCase
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

    @Provides
    @Singleton
    fun provideGetStudiesListUseCase(studiesRepository: StudiesRepository): GetStudiesListUseCase = GetStudiesListUseCase(studiesRepository)

    @Provides
    @Singleton
    fun provideCreateStudiesUseCase(studiesRepository: StudiesRepository): CreateStudiesUseCase = CreateStudiesUseCase(studiesRepository)

    @Provides
    @Singleton
    fun provideGetStudiesDetailUseCase(studiesRepository: StudiesRepository): GetStudiesDetailUseCase =
        GetStudiesDetailUseCase(studiesRepository)

    @Provides
    @Singleton
    fun provideUpdateStudiesUseCase(studiesRepository: StudiesRepository): UpdateStudiesUseCase = UpdateStudiesUseCase(studiesRepository)

    @Provides
    @Singleton
    fun provideDeleteStudiesUseCase(studiesRepository: StudiesRepository): DeleteStudiesUseCase = DeleteStudiesUseCase(studiesRepository)

    @Provides
    @Singleton
    fun provideApplyStudiesUseCase(studiesRepository: StudiesRepository): ApplyStudiesUseCase = ApplyStudiesUseCase(studiesRepository)

    @Provides
    @Singleton
    fun provideCancelApplicationsStudiesUseCase(studiesRepository: StudiesRepository): CancelApplicationsStudiesUseCase =
        CancelApplicationsStudiesUseCase(studiesRepository)

    @Provides
    @Singleton
    fun provideGetStudiesMembersUseCase(studiesRepository: StudiesRepository): GetStudiesMembersUseCase =
        GetStudiesMembersUseCase(studiesRepository)
}
