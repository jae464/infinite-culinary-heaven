package com.jae464.data.di

import com.jae464.data.remote.api.AuthService
import com.jae464.data.remote.api.ContestService
import com.jae464.data.remote.api.RecipeService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiServiceModule {

    @Provides
    @Singleton
    fun provideContestService(
        @NoAuthRetrofit retrofit: Retrofit
    ): ContestService = retrofit.create(ContestService::class.java)

    @Provides
    @Singleton
    fun provideRecipeService(
        @AuthRetrofit retrofit: Retrofit
    ): RecipeService = retrofit.create(RecipeService::class.java)

    @Provides
    @Singleton
    fun provideAuthService(
        @NoAuthRetrofit retrofit: Retrofit
    ): AuthService = retrofit.create(AuthService::class.java)

}