package com.jae464.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    private const val AUTH_PREFERENCES = "auth_preferences"
    private const val BOOK_MARK_PREFERENCES = "book_mark_preferences"
    private const val SETTING_PREFERENCES = "setting_preferences"

    @Provides
    @Singleton
    @AuthDataStore
    fun provideAuthDataStore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            produceFile = { context.preferencesDataStoreFile(AUTH_PREFERENCES) }
        )
    }

    @Provides
    @Singleton
    @BookMarkDataStore
    fun provideBookMarkDataStore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            produceFile = { context.preferencesDataStoreFile(BOOK_MARK_PREFERENCES) }
        )
    }

    @Provides
    @Singleton
    @SettingDataStore
    fun provideSettingDataStore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            produceFile = { context.preferencesDataStoreFile(SETTING_PREFERENCES) }
        )
    }


}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthDataStore

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BookMarkDataStore

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class SettingDataStore
