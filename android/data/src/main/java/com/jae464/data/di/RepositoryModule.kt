package com.jae464.data.di

import com.jae464.data.repository.FakeContestRepository
import com.jae464.data.repository.FakeRecipeRepository
import com.jae464.domain.repository.ContestRepository
import com.jae464.domain.repository.RecipeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindRecipeRepository(
        recipeRepositoryImpl: FakeRecipeRepository
    ): RecipeRepository

    @Binds
    @Singleton
    abstract fun bindContestRepository(
        contestRepositoryImpl: FakeContestRepository
    ): ContestRepository


}