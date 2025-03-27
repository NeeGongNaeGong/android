package com.ssafy.neegongnaegong.module.di

import com.ssafy.neegongnaegong.data.remote.GitHubApi
import com.ssafy.neegongnaegong.data.remote.UserCalendarApi
import com.ssafy.neegongnaegong.data.remote.StudiesApi
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
    fun provideRetrofit(): Retrofit =
        Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideGitHubApi(retrofit: Retrofit): GitHubApi = retrofit.create(GitHubApi::class.java)

    @Provides
    @Singleton
    fun provideUserCalendarApi(retrofit: Retrofit): UserCalendarApi = retrofit.create(UserCalendarApi::class.java)

    @Provides
    @Singleton
    fun provideGroupApi(retrofit: Retrofit): StudiesApi = retrofit.create(StudiesApi::class.java)
}
