package com.ssafy.neegongnaegong.module.di.usecase

import com.ssafy.neegongnaegong.domain.repository.AuthRepository
import com.ssafy.neegongnaegong.domain.repository.UserRepository
import com.ssafy.neegongnaegong.domain.usecase.auth.LoginUseCase
import com.ssafy.neegongnaegong.domain.usecase.auth.RegisterUseCase
import com.ssafy.neegongnaegong.domain.usecase.auth.ReissueUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthUseCaseModule {
    @Provides
    @Singleton
    fun provideLoginUseCase(authRepository: AuthRepository): LoginUseCase = LoginUseCase(authRepository)

    @Provides
    @Singleton
    fun provideReissueUseCase(authRepository: AuthRepository, userRepository: UserRepository): ReissueUseCase = ReissueUseCase(authRepository, userRepository)

    @Provides
    @Singleton
    fun provideRegisterUseCase(authRepository: AuthRepository): RegisterUseCase = RegisterUseCase(authRepository)
}
