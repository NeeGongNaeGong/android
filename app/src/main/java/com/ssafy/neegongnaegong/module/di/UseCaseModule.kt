package com.ssafy.neegongnaegong.module.di

import com.ssafy.neegongnaegong.domain.repository.AuthRepository
import com.ssafy.neegongnaegong.domain.repository.CalendarRepository
import com.ssafy.neegongnaegong.domain.repository.GitHubRepository
import com.ssafy.neegongnaegong.domain.repository.StudiesRepository
import com.ssafy.neegongnaegong.domain.repository.UserRepository
import com.ssafy.neegongnaegong.domain.usecase.GetStudiesUseCase
import com.ssafy.neegongnaegong.domain.usecase.GetUserReposUseCase
import com.ssafy.neegongnaegong.domain.usecase.auth.LoginUseCase
import com.ssafy.neegongnaegong.domain.usecase.auth.LogoutUseCase
import com.ssafy.neegongnaegong.domain.usecase.auth.RegisterUseCase
import com.ssafy.neegongnaegong.domain.usecase.auth.ReissueUseCase
import com.ssafy.neegongnaegong.domain.usecase.calendar.CreatePersonalSchedulesUseCase
import com.ssafy.neegongnaegong.domain.usecase.calendar.DeletePersonalSchedulesUseCase
import com.ssafy.neegongnaegong.domain.usecase.calendar.GetScheduleDetailUseCase
import com.ssafy.neegongnaegong.domain.usecase.calendar.GetUserSchedulesUseCase
import com.ssafy.neegongnaegong.domain.usecase.calendar.UpdatePersonalSchedulesUseCase
import com.ssafy.neegongnaegong.domain.usecase.user.GetCurrentUserUseCase
import com.ssafy.neegongnaegong.domain.usecase.user.GetUserUseCase
import com.ssafy.neegongnaegong.domain.usecase.user.UpdateNicknameUseCase
import com.ssafy.neegongnaegong.domain.usecase.user.UpdateProfileImageUseCase
import com.ssafy.neegongnaegong.domain.usecase.user.ValidateNicknameUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    // Auth UseCases
    @Provides
    @Singleton
    fun provideLoginUseCase(authRepository: AuthRepository): LoginUseCase = LoginUseCase(authRepository)

    @Provides
    @Singleton
    fun provideLogoutUseCase(authRepository: AuthRepository): LogoutUseCase = LogoutUseCase(authRepository)

    @Provides
    @Singleton
    fun provideReissueUseCase(authRepository: AuthRepository): ReissueUseCase = ReissueUseCase(authRepository)

    @Provides
    @Singleton
    fun provideRegisterUseCase(authRepository: AuthRepository): RegisterUseCase = RegisterUseCase(authRepository)

    // Calendar UseCases
    @Provides
    @Singleton
    fun provideCreatePersonalSchedulesUseCase(calendarRepository: CalendarRepository): CreatePersonalSchedulesUseCase =
        CreatePersonalSchedulesUseCase(calendarRepository)

    @Provides
    @Singleton
    fun provideDeletePersonalSchedulesUseCase(calendarRepository: CalendarRepository): DeletePersonalSchedulesUseCase =
        DeletePersonalSchedulesUseCase(calendarRepository)

    @Provides
    @Singleton
    fun provideGetScheduleDetailUseCase(calendarRepository: CalendarRepository): GetScheduleDetailUseCase =
        GetScheduleDetailUseCase(calendarRepository)

    @Provides
    @Singleton
    fun provideGetUserSchedulesUseCase(calendarRepository: CalendarRepository): GetUserSchedulesUseCase =
        GetUserSchedulesUseCase(calendarRepository)

    @Provides
    @Singleton
    fun provideUpdatePersonalSchedulesUseCase(calendarRepository: CalendarRepository): UpdatePersonalSchedulesUseCase =
        UpdatePersonalSchedulesUseCase(calendarRepository)

    // User UseCases
    @Provides
    @Singleton
    fun provideGetCurrentUserUseCase(userRepository: UserRepository): GetCurrentUserUseCase = GetCurrentUserUseCase(userRepository)

    @Provides
    @Singleton
    fun provideGetUserUseCase(userRepository: UserRepository): GetUserUseCase = GetUserUseCase(userRepository)

    @Provides
    @Singleton
    fun provideUpdateNicknameUseCase(userRepository: UserRepository): UpdateNicknameUseCase = UpdateNicknameUseCase(userRepository)

    @Provides
    @Singleton
    fun provideUpdateProfileImageUseCase(userRepository: UserRepository): UpdateProfileImageUseCase =
        UpdateProfileImageUseCase(userRepository)

    @Provides
    @Singleton
    fun provideValidateNicknameUseCase(userRepository: UserRepository): ValidateNicknameUseCase = ValidateNicknameUseCase(userRepository)

    // Study UseCase
    @Provides
    @Singleton
    fun provideGetStudiesUseCase(studiesRepository: StudiesRepository): GetStudiesUseCase = GetStudiesUseCase(studiesRepository)

    // GitHub UseCase
    @Provides
    @Singleton
    fun provideGetUserReposUseCase(gitHubRepository: GitHubRepository): GetUserReposUseCase = GetUserReposUseCase(gitHubRepository)
}
