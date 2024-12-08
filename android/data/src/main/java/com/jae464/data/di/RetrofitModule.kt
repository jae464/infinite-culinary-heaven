package com.jae464.data.di

import com.jae464.domain.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.io.IOException
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    private val BASE_URL = "http://10.0.2.2:8080/"
//    private val BASE_URL = "http://52.78.188.164:8080/"
//    private val BASE_URL = "http://3.36.98.149:8080/"

    @Provides
    @Singleton
    @AuthInterceptor
    fun provideAuthInterceptor(
        authRepository: AuthRepository
    ): Interceptor {
        return Interceptor { chain ->
            val accessToken = runBlocking {
                authRepository.getAccessToken()
            }
            val request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $accessToken")
                .build()

            val response = chain.proceed(request)

            if (response.code == 401) {
                response.close()

                runBlocking {
                    authRepository.refreshToken()
                        .onFailure {
//                            throw IOException(it.message)
                        }
                }

                val newAccessToken = runBlocking {
                    authRepository.getAccessToken()
                }

                val newRequest = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer $newAccessToken")
                    .build()

                return@Interceptor chain.proceed(newRequest)
            }
            return@Interceptor response
        }
    }

    @Provides
    @Singleton
    @NoAuthHttpClient
    fun provideNoAuthOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .apply {
            addInterceptor(
                HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
            )
        }.build()

    @Provides
    @Singleton
    @AuthHttpClient
    fun provideAuthOkHttpClient(
        @AuthInterceptor authInterceptor: Interceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .apply {
            addInterceptor(
                HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
            )
            addInterceptor(authInterceptor)
        }.build()

    @Provides
    @Singleton
    @NoAuthRetrofit
    fun provideNoAuthRetrofit(
        @NoAuthHttpClient okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .build()

    @Provides
    @Singleton
    @AuthRetrofit
    fun provideAuthRetrofit(
        @AuthHttpClient okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .build()

}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NoAuthHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NoAuthRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthInterceptor
