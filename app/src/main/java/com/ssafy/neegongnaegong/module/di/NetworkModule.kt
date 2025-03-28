package com.ssafy.neegongnaegong.module.di

import com.ssafy.neegongnaegong.BuildConfig
import com.ssafy.neegongnaegong.data.remote.GitHubApi
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
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit
            .Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideGitHubApi(retrofit: Retrofit): GitHubApi = retrofit.create(GitHubApi::class.java)

    @Provides
    @Singleton
    fun provideGroupApi(retrofit: Retrofit): StudiesApi = retrofit.create(StudiesApi::class.java)
}
