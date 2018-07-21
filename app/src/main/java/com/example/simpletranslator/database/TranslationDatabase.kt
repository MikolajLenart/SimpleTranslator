package com.example.simpletranslator.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = [TranslationEntity::class], version = 1)
abstract class TranslationDatabase: RoomDatabase() {

    abstract fun translationDao(): TranslationDao
}