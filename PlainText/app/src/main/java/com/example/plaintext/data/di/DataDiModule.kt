package com.example.plaintext.data.di


import android.content.Context
import androidx.room.Room
import com.example.plaintext.data.PlainTextDatabase
import com.example.plaintext.data.dao.PasswordDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataDiModule {

    @Provides
    @Singleton
    fun providePasswordDBStore(@ApplicationContext context: Context): PlainTextDatabase {
        return Room.databaseBuilder(
            context,
            PlainTextDatabase::class.java,
            "app_database"
        ).build()
    }

    @Singleton
    @Provides
    fun providePasswordDao(plainTextDatabase: PlainTextDatabase): PasswordDao {
        return plainTextDatabase.passwordDao()
    }
}