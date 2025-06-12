package com.ssafy.neegongnaegong.module.di.usecase

import com.ssafy.neegongnaegong.domain.repository.FileRepository
import com.ssafy.neegongnaegong.domain.usecase.file.IssuePresignedUrlUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FileUseCaseModule {
    @Provides
    @Singleton
    fun provideIssuePresignedUrlUseCase(fileRepository: FileRepository): IssuePresignedUrlUseCase = IssuePresignedUrlUseCase(fileRepository)
}
