package com.example.simpletranslator.application

import com.example.simpletranslator.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector()
    abstract fun provideActivity(): MainActivity
}