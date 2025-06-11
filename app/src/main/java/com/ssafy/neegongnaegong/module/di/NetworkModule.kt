package com.ssafy.neegongnaegong.module.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ssafy.neegongnaegong.BuildConfig
import com.ssafy.neegongnaegong.data.remote.AuthApi
import com.ssafy.neegongnaegong.data.remote.CategoryApi
import com.ssafy.neegongnaegong.data.remote.FileApi
import com.ssafy.neegongnaegong.data.remote.LearningRecordApi
import com.ssafy.neegongnaegong.data.remote.NotificationApi
import com.ssafy.neegongnaegong.data.remote.S3Api
import com.ssafy.neegongnaegong.data.remote.StudiesApi
import com.ssafy.neegongnaegong.data.remote.StudyGroupApi
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
import retrofit2.create
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
    ): Retrofit =
        Retrofit
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
    ): Retrofit =
        Retrofit
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
    @S3Retrofit
    fun provideS3Retrofit(
        @S3OkHttpClient okHttpClient: OkHttpClient,
    ): Retrofit =
        Retrofit
            .Builder()
            .baseUrl("https://dummy/")
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    @AuthOkHttpClient
    fun provideAuthOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    setLevel(HttpLoggingInterceptor.Level.BODY)
                },
            )
            .readTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
            .connectTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
            .build()

    @Singleton
    @Provides
    @SecureOkHttpClient
    fun provideSecureOkHttpClient(
        authInterceptor: AuthInterceptor,
        reissueAuthenticator: ReissueAuthenticator,
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    setLevel(HttpLoggingInterceptor.Level.BODY)
                },
            )
            .authenticator(reissueAuthenticator)
            .readTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
            .connectTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
            .build()

    @Singleton
    @Provides
    @S3OkHttpClient
    fun provideS3OkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    setLevel(HttpLoggingInterceptor.Level.BODY)
                },
            )
            .build()

    @Provides
    @Singleton
    fun provideAuthApi(
        @AuthRetrofit retrofit: Retrofit,
    ): AuthApi = retrofit.create(AuthApi::class.java)

    @Provides
    @Singleton
    fun provideUserApi(
        @SecureRetrofit retrofit: Retrofit,
    ): UserApi = retrofit.create(UserApi::class.java)

    @Provides
    @Singleton
    fun provideUserCalendarApi(
        @SecureRetrofit retrofit: Retrofit,
    ): UserCalendarApi = retrofit.create(UserCalendarApi::class.java)

    @Provides
    @Singleton
    fun provideGroupApi(
        @SecureRetrofit retrofit: Retrofit,
    ): StudiesApi = retrofit.create(StudiesApi::class.java)

    @Provides
    @Singleton
    fun provideStudyGroupApi(
        @SecureRetrofit retrofit: Retrofit,
    ): StudyGroupApi = retrofit.create(StudyGroupApi::class.java)

    @Provides
    @Singleton
    fun provideCategoryApi(
        @SecureRetrofit retrofit: Retrofit,
    ): CategoryApi = retrofit.create(CategoryApi::class.java)

    @Provides
    @Singleton
    fun provideFileApi(
        @SecureRetrofit retrofit: Retrofit,
    ): FileApi = retrofit.create(FileApi::class.java)

    @Provides
    @Singleton
    fun provideS3Api(
        @S3Retrofit retrofit: Retrofit,
    ): S3Api = retrofit.create(S3Api::class.java)

    @Provides
    @Singleton
    fun provideLearningRecordApi(
        @SecureRetrofit retrofit: Retrofit,
    ): LearningRecordApi = retrofit.create(LearningRecordApi::class.java)

    @Provides
    @Singleton
    fun provideNotificationApi(
        @SecureRetrofit retrofit: Retrofit,
    ): NotificationApi = retrofit.create()
}
