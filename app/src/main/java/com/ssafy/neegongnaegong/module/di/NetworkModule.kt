package com.ssafy.neegongnaegong.module.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ssafy.neegongnaegong.BuildConfig
import com.ssafy.neegongnaegong.data.remote.AuthApi
import com.ssafy.neegongnaegong.data.remote.GitHubApi
import com.ssafy.neegongnaegong.data.remote.StudiesApi
import com.ssafy.neegongnaegong.data.remote.UserApi
import com.ssafy.neegongnaegong.data.remote.UserCalendarApi
import com.ssafy.neegongnaegong.data.remote.adapter.call.ConvertToResultAdapterFactory
import com.ssafy.neegongnaegong.data.remote.adapter.json.LocalDateAdapter
import com.ssafy.neegongnaegong.data.remote.adapter.json.LocalDateTimeAdapter
import com.ssafy.neegongnaegong.data.remote.authenticator.ReissueAuthenticator
import com.ssafy.neegongnaegong.data.remote.converter.ListQueryConverter
import com.ssafy.neegongnaegong.data.remote.converter.NullOnEmptyConverter
import com.ssafy.neegongnaegong.data.remote.interceptor.AuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val TIME_OUT = 5000L

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            .registerTypeAdapter(LocalDate::class.java, LocalDateAdapter())
            .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeAdapter())
            .create()
    }

    @Provides
    @Singleton
    @AuthRetrofit
    fun provideAuthRetrofit(
        @AuthOkHttpClient okHttpClient: OkHttpClient,
        gson: Gson,
    ): Retrofit = Retrofit
        .Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addConverterFactory(ListQueryConverter())
        .addConverterFactory(NullOnEmptyConverter())
        .addCallAdapterFactory(ConvertToResultAdapterFactory())
        .build()

    @Provides
    @Singleton
    @SecureRetrofit
    fun provideSecureRetrofit(
        @SecureOkHttpClient okHttpClient: OkHttpClient,
        gson: Gson,
    ): Retrofit = Retrofit
        .Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addConverterFactory(ListQueryConverter())
        .addConverterFactory(NullOnEmptyConverter())
        .addCallAdapterFactory(ConvertToResultAdapterFactory())
        .build()

    @Provides
    @Singleton
    @AuthOkHttpClient
    fun provideAuthOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
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
        reissueAuthenticator: ReissueAuthenticator
    ): OkHttpClient = OkHttpClient.Builder()
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
