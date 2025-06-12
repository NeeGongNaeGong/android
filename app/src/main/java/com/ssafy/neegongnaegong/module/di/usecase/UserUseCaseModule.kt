package com.ssafy.neegongnaegong.module.di.usecase

import com.ssafy.neegongnaegong.domain.repository.UserRepository
import com.ssafy.neegongnaegong.domain.usecase.user.GetUserUseCase
import com.ssafy.neegongnaegong.domain.usecase.user.UpdateProfileImageUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserUseCaseModule {
    @Provides
    @Singleton
    fun provideGetUserUseCase(userRepository: UserRepository): GetUserUseCase = GetUserUseCase(userRepository)

    @Provides
    @Singleton
    fun provideUpdateProfileImageUseCase(userRepository: UserRepository): UpdateProfileImageUseCase =
        UpdateProfileImageUseCase(userRepository)
}
