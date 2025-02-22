package com.ssafy.neegongnaegong.module.di

import com.ssafy.neegongnaegong.domain.repository.GitHubRepository
import com.ssafy.neegongnaegong.domain.usecase.GetUserReposUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    @ViewModelScoped
    fun provideGetUserReposUseCase(repository: GitHubRepository): GetUserReposUseCase = GetUserReposUseCase(repository)
}
