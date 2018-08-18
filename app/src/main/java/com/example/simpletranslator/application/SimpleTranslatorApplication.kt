package com.example.simpletranslator.application

import android.arch.persistence.room.Room
import com.example.simpletranslator.database.TranslationDatabase
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import okhttp3.HttpUrl

class SimpleTranslatorApplication: DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> = DaggerAppComponent
            .builder()
            .baseURl(HttpUrl.parse("https://glosbe.com/gapi/")!!)
            .database(Room.databaseBuilder(this,
                    TranslationDatabase::class.java, "translationDB").build())
            .create(this)
}