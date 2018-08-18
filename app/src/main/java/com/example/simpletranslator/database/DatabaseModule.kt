package com.example.simpletranslator.database

import dagger.Module
import dagger.Provides

@Module
class DatabaseModule {

    @Provides
    fun provideDao(database: TranslationDatabase) = database.translationDao()
}