package com.jae464.data.di

import com.jae464.data.remote.model.response.CommentResponse
import com.jae464.data.repository.DefaultAuthRepository
import com.jae464.data.repository.DefaultBookMarkRepository
import com.jae464.data.repository.DefaultCommentRepository
import com.jae464.data.repository.DefaultContestRepository
import com.jae464.data.repository.DefaultDeviceTokenRepository
import com.jae464.data.repository.DefaultRecipeRepository
import com.jae464.data.repository.DefaultUserRepository
import com.jae464.data.repository.FakeBookMarkRepository
import com.jae464.data.repository.FakeAuthRepository
import com.jae464.domain.repository.BookMarkRepository
import com.jae464.domain.repository.ContestRepository
import com.jae464.domain.repository.RecipeRepository
import com.jae464.domain.repository.AuthRepository
import com.jae464.domain.repository.CommentRepository
import com.jae464.domain.repository.DeviceTokenRepository
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
    abstract fun bindAuthRepository(
        authRepositoryImpl: DefaultAuthRepository
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindDeviceTokenRepository(
        deviceTokenRepositoryImpl: DefaultDeviceTokenRepository
    ): DeviceTokenRepository

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
    abstract fun bindBookMarkRepository(
        bookMarkRepositoryImpl: DefaultBookMarkRepository
    ): BookMarkRepository

    @Binds
    @Singleton
    abstract fun bindUserRepository(
        userRepositoryImpl: DefaultUserRepository
    ): UserRepository

    @Binds
    @Singleton
    abstract fun bindCommentRepository(
        commentRepositoryImpl: DefaultCommentRepository
    ): CommentRepository

}