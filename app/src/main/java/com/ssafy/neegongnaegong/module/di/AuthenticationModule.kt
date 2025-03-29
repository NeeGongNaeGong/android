package com.ssafy.neegongnaegong.module.di

import com.ssafy.neegongnaegong.data.local.TokenManager
import com.ssafy.neegongnaegong.data.local.TokenManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AuthenticationModule {
    @Singleton
    @Binds
    fun bindTokenManager(tokenManagerImpl: TokenManagerImpl): TokenManager
}