package com.example.simpletranslator.database

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "translations")
data class TranslationEntity(var sourcedPhrase: String,
                             var translatedPhrase: String){

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}