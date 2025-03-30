package com.ssafy.neegongnaegong.module.di

import com.ssafy.neegongnaegong.data.local.DataStoreManager
import com.ssafy.neegongnaegong.data.local.LocalStorageManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface LocalStorageModule {
    @Singleton
    @Binds
    fun bindLocalStorageManager(localStorageManager: DataStoreManager): LocalStorageManager
}