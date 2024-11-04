package com.jae464.data.di

import com.jae464.data.remote.api.ContestService
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

}