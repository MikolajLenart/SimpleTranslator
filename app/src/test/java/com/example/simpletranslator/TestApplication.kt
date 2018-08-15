package com.example.simpletranslator

import com.example.simpletranslator.translation.TranslationViewModelFactory
import com.nhaarman.mockito_kotlin.mock
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import dagger.android.support.DaggerApplication
import javax.inject.Singleton

class TestApplication(): DaggerApplication() {

    val testComponent by lazy {
        DaggerTestComponent.builder().create(this) as TestComponent
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return testComponent
    }
}
@Singleton
@Component(modules = [AndroidInjectionModule::class, AndroidSupportInjectionModule::class, TestActivityModule::class, TestModule::class])
interface TestComponent: AndroidInjector<TestApplication> {

    @Component.Builder
    abstract class Builder: AndroidInjector.Builder<TestApplication>(){}

    fun inject(test: MainActivityTest)
}

@Module
class TestModule {

    @Provides
    @Singleton
    fun provideMockedFactory() = mock<TranslationViewModelFactory> { }
}

@Module
abstract class TestActivityModule {

    @ContributesAndroidInjector()
    abstract fun provideActivity(): MainActivity
}
