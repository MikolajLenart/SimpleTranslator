package com.example.simpletranslator.repository

import com.example.simpletranslator.database.TranslationDao
import com.example.simpletranslator.database.TranslationEntity
import com.example.simpletranslator.rest.api.TranslationService

class TranslationRepository(val service: TranslationService, val dao: TranslationDao) {

    fun getTranslation(phrase: String) =
            dao.checkIfTranslationExists(phrase).flatMap {
                if (it) dao.getTranslation(phrase) else
                    service.translate(phrase)
                            .map {
                                val translationResult = it.tuc?.firstOrNull()?.phrase?.text
                                if (translationResult != null) TranslationEntity(phrase, translationResult) else throw NullPointerException()
                            }.singleOrError()
                            .doOnSuccess {
                                dao.insertTranslation(it)
                            }
                            .flatMap {
                                dao.getTranslation(it.sourcedPhrase)
                            }
            }
}