package com.ssafy.neegongnaegong.module.di.usecase

import com.ssafy.neegongnaegong.domain.repository.S3Repository
import com.ssafy.neegongnaegong.domain.usecase.s3.UploadImageToS3UseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object S3UseCaseModule {
    @Provides
    @Singleton
    fun provideUploadImageToS3UseCase(s3Repository: S3Repository): UploadImageToS3UseCase = UploadImageToS3UseCase(s3Repository)
}
