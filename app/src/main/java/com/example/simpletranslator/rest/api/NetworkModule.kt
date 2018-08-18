package com.example.simpletranslator.rest.api

import dagger.Module
import dagger.Provides
import okhttp3.HttpUrl
import retrofit2.Retrofit

@Module
class NetworkModule {

    @Provides
    fun provideRetorfit(baseUrl: HttpUrl) = ApiConfiguration().createConfiguration(baseUrl)

    @Provides
    fun provideTranslationService(retrofit: Retrofit) = retrofit.create(TranslationService::class.java)
}