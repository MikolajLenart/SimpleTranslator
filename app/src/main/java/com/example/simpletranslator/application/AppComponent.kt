package com.example.simpletranslator.application

import com.example.simpletranslator.database.DatabaseModule
import com.example.simpletranslator.database.TranslationDatabase
import com.example.simpletranslator.rest.api.NetworkModule
import com.example.simpletranslator.translation.TranslationModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import okhttp3.HttpUrl

@Component(modules = [NetworkModule::class, DatabaseModule::class, AndroidSupportInjectionModule::class, ActivityBuilder::class, TranslationModule::class])
interface AppComponent: AndroidInjector<SimpleTranslatorApplication> {

    @Component.Builder
    abstract class Builder: AndroidInjector.Builder<SimpleTranslatorApplication>(){

        @BindsInstance
        abstract fun database(database: TranslationDatabase): Builder

        @BindsInstance
        abstract fun baseURl(baseUrl: HttpUrl): Builder
    }
}