package com.jae464.data.di

import com.jae464.data.repository.DefaultContestRepository
import com.jae464.data.repository.DefaultRecipeRepository
import com.jae464.data.repository.FakeBookMarkRepository
import com.jae464.data.repository.FakeContestRepository
import com.jae464.data.repository.FakeRecipeRepository
import com.jae464.data.repository.FakeUserRepository
import com.jae464.domain.repository.BookMarkRepository
import com.jae464.domain.repository.ContestRepository
import com.jae464.domain.repository.RecipeRepository
import com.jae464.domain.repository.UserRepository
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
        recipeRepositoryImpl: DefaultRecipeRepository
    ): RecipeRepository

    @Binds
    @Singleton
    abstract fun bindContestRepository(
        contestRepositoryImpl: DefaultContestRepository
    ): ContestRepository

    @Binds
    @Singleton
    abstract fun bindUserRepository(
        userRepositoryImpl: FakeUserRepository
    ): UserRepository

    @Binds
    @Singleton
    abstract fun bindBookMarkRepository(
        bookMarkRepositoryImpl: FakeBookMarkRepository
    ): BookMarkRepository



}