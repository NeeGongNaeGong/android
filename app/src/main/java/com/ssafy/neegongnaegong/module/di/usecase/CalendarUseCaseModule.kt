package com.ssafy.neegongnaegong.module.di.usecase

import com.ssafy.neegongnaegong.domain.repository.CalendarRepository
import com.ssafy.neegongnaegong.domain.usecase.calendar.CreatePersonalSchedulesUseCase
import com.ssafy.neegongnaegong.domain.usecase.calendar.DeletePersonalSchedulesUseCase
import com.ssafy.neegongnaegong.domain.usecase.calendar.GetScheduleDetailUseCase
import com.ssafy.neegongnaegong.domain.usecase.calendar.GetUserSchedulesUseCase
import com.ssafy.neegongnaegong.domain.usecase.calendar.UpdatePersonalSchedulesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CalendarUseCaseModule {
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
}
