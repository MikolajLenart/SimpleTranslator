package com.example.simpletranslator.translation

import com.example.simpletranslator.repository.TranslationRepository
import dagger.Module
import dagger.Provides

@Module
class TranslationModule {

    @Provides
    fun provideViewModelFactory(repository: TranslationRepository) = TranslationViewModelFactory(repository)
}