package com.ssafy.neegongnaegong.module.di

import com.ssafy.neegongnaegong.data.remote.GitHubApi
import com.ssafy.neegongnaegong.data.remote.UserCalendarApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    // Server꺼는 local properties에 숨김처리
    private const val BASE_URL = "https://api.github.com/"

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideGitHubApi(retrofit: Retrofit): GitHubApi {
        return retrofit.create(GitHubApi::class.java)
    }

    @Provides
    @Singleton
    fun provideUserCalendarApi(retrofit: Retrofit): UserCalendarApi {
        return retrofit.create(UserCalendarApi::class.java)
    }
}
