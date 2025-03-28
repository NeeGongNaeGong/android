package com.ssafy.neegongnaegong.module.di

import com.ssafy.neegongnaegong.data.remote.TokenManager
import com.ssafy.neegongnaegong.data.remote.utils.KeyStoreTokenManager
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
    fun bindTokenManager(keystoreTokenManager: KeyStoreTokenManager): TokenManager
}