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
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    // Auth UseCases
    @Provides
    @ViewModelScoped
    fun provideLoginUseCase(authRepository: AuthRepository): LoginUseCase =
        LoginUseCase(authRepository)

    @Provides
    @ViewModelScoped
    fun provideLogoutUseCase(authRepository: AuthRepository): LogoutUseCase =
        LogoutUseCase(authRepository)

    @Provides
    @ViewModelScoped
    fun provideReissueUseCase(authRepository: AuthRepository): ReissueUseCase =
        ReissueUseCase(authRepository)

    @Provides
    @ViewModelScoped
    fun provideRegisterUseCase(authRepository: AuthRepository): RegisterUseCase =
        RegisterUseCase(authRepository)

    // Calendar UseCases
    @Provides
    @ViewModelScoped
    fun provideCreatePersonalSchedulesUseCase(calendarRepository: CalendarRepository): CreatePersonalSchedulesUseCase =
        CreatePersonalSchedulesUseCase(calendarRepository)

    @Provides
    @ViewModelScoped
    fun provideDeletePersonalSchedulesUseCase(calendarRepository: CalendarRepository): DeletePersonalSchedulesUseCase =
        DeletePersonalSchedulesUseCase(calendarRepository)

    @Provides
    @ViewModelScoped
    fun provideGetScheduleDetailUseCase(calendarRepository: CalendarRepository): GetScheduleDetailUseCase =
        GetScheduleDetailUseCase(calendarRepository)

    @Provides
    @ViewModelScoped
    fun provideGetUserSchedulesUseCase(calendarRepository: CalendarRepository): GetUserSchedulesUseCase =
        GetUserSchedulesUseCase(calendarRepository)

    @Provides
    @ViewModelScoped
    fun provideUpdatePersonalSchedulesUseCase(calendarRepository: CalendarRepository): UpdatePersonalSchedulesUseCase =
        UpdatePersonalSchedulesUseCase(calendarRepository)

    // User UseCases
    @Provides
    @ViewModelScoped
    fun provideGetCurrentUserUseCase(userRepository: UserRepository): GetCurrentUserUseCase =
        GetCurrentUserUseCase(userRepository)

    @Provides
    @ViewModelScoped
    fun provideGetUserUseCase(userRepository: UserRepository): GetUserUseCase =
        GetUserUseCase(userRepository)

    @Provides
    @ViewModelScoped
    fun provideUpdateNicknameUseCase(userRepository: UserRepository): UpdateNicknameUseCase =
        UpdateNicknameUseCase(userRepository)

    @Provides
    @ViewModelScoped
    fun provideUpdateProfileImageUseCase(userRepository: UserRepository): UpdateProfileImageUseCase =
        UpdateProfileImageUseCase(userRepository)

    @Provides
    @ViewModelScoped
    fun provideValidateNicknameUseCase(userRepository: UserRepository): ValidateNicknameUseCase =
        ValidateNicknameUseCase(userRepository)

    // Study UseCase
    @Provides
    @ViewModelScoped
    fun provideGetStudiesUseCase(studiesRepository: StudiesRepository): GetStudiesUseCase =
        GetStudiesUseCase(studiesRepository)

    // GitHub UseCase
    @Provides
    @ViewModelScoped
    fun provideGetUserReposUseCase(gitHubRepository: GitHubRepository): GetUserReposUseCase =
        GetUserReposUseCase(gitHubRepository)
}
