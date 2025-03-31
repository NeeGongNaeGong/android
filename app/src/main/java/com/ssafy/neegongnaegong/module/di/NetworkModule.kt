package com.ssafy.neegongnaegong.module.di

import com.ssafy.neegongnaegong.BuildConfig
import com.ssafy.neegongnaegong.data.remote.AuthApi
import com.ssafy.neegongnaegong.data.remote.GitHubApi
import com.ssafy.neegongnaegong.data.remote.StudiesApi
import com.ssafy.neegongnaegong.data.remote.UserApi
import com.ssafy.neegongnaegong.data.remote.UserCalendarApi
import com.ssafy.neegongnaegong.data.remote.authenticator.ReissueAuthenticator
import com.ssafy.neegongnaegong.data.remote.converter.ListQueryConverter
import com.ssafy.neegongnaegong.data.remote.interceptor.AuthInterceptor
import com.ssafy.neegongnaegong.data.remote.interceptor.NetworkErrorInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val TIME_OUT = 5000L

    @Provides
    @Singleton
    @AuthRetrofit
    fun provideAuthRetrofit(
        @AuthOkHttpClient okHttpClient: OkHttpClient,
    ): Retrofit = Retrofit
        .Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(ListQueryConverter())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    @SecureRetrofit
    fun provideSecureRetrofit(
        @SecureOkHttpClient okHttpClient: OkHttpClient,
    ): Retrofit = Retrofit
        .Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(ListQueryConverter())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    @AuthOkHttpClient
    fun provideAuthOkHttpClient(
        networkErrorInterceptor: NetworkErrorInterceptor,
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(networkErrorInterceptor)
        .addInterceptor(HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        })
        .readTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
        .connectTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
        .build()

    @Singleton
    @Provides
    @SecureOkHttpClient
    fun provideSecureOkHttpClient(
        authInterceptor: AuthInterceptor,
        networkErrorInterceptor: NetworkErrorInterceptor,
        reissueAuthenticator: ReissueAuthenticator
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(networkErrorInterceptor)
        .addInterceptor(authInterceptor)
        .addInterceptor(HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        })
        .authenticator(reissueAuthenticator)
        .readTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
        .connectTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
        .build()

    @Provides
    @Singleton
    fun provideGitHubApi(@AuthRetrofit retrofit: Retrofit): GitHubApi = retrofit.create(GitHubApi::class.java)

    @Provides
    @Singleton
    fun provideAuthApi(@AuthRetrofit retrofit: Retrofit): AuthApi = retrofit.create(AuthApi::class.java)

    @Provides
    @Singleton
    fun provideUserApi(@SecureRetrofit retrofit: Retrofit): UserApi = retrofit.create(UserApi::class.java)

    @Provides
    @Singleton
    fun provideUserCalendarApi(@SecureRetrofit retrofit: Retrofit): UserCalendarApi = retrofit.create(UserCalendarApi::class.java)

    @Provides
    @Singleton
    fun provideGroupApi(@SecureRetrofit retrofit: Retrofit): StudiesApi = retrofit.create(StudiesApi::class.java)
}
