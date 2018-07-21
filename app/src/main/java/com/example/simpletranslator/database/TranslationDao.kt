package com.example.simpletranslator.database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import io.reactivex.Single

@Dao
interface TranslationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTranslation(entity: TranslationEntity)

    @Query("SELECT translatedPhrase FROM translations " +
            "WHERE sourcedPhrase = :sourcedPhrase LIMIT 1")
    fun getTranslation(sourcedPhrase: String): Single<String>

    @Query("SELECT EXISTS(SELECT translatedPhrase FROM translations " +
            "WHERE sourcedPhrase = :sourcedPhrase LIMIT 1)")
    fun checkIfTranslationExists(sourcedPhrase: String): Single<Boolean>
}