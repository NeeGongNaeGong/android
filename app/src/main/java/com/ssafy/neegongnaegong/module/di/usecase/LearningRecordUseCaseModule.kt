package com.ssafy.neegongnaegong.module.di.usecase

import com.ssafy.neegongnaegong.domain.repository.LearningRecordRepository
import com.ssafy.neegongnaegong.domain.usecase.learningrecord.CreateLearningRecordUseCase
import com.ssafy.neegongnaegong.domain.usecase.learningrecord.DeleteLearningRecordUseCase
import com.ssafy.neegongnaegong.domain.usecase.learningrecord.UpdateLearningRecordUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LearningRecordUseCaseModule {
    @Provides
    @Singleton
    fun provideCreateLearningRecordUseCase(learningRecordRepository: LearningRecordRepository): CreateLearningRecordUseCase =
        CreateLearningRecordUseCase(
            learningRecordRepository,
        )

    @Provides
    @Singleton
    fun provideUpdateLearningRecordUseCase(learningRecordRepository: LearningRecordRepository): UpdateLearningRecordUseCase =
        UpdateLearningRecordUseCase(
            learningRecordRepository,
        )

    @Provides
    @Singleton
    fun provideDeleteLearningRecordUseCase(learningRecordRepository: LearningRecordRepository): DeleteLearningRecordUseCase =
        DeleteLearningRecordUseCase(
            learningRecordRepository,
        )
}
