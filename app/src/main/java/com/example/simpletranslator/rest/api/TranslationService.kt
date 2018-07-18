package com.example.simpletranslator.rest.api

import com.example.simpletranslator.rest.api.model.Translate
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface TranslationService {
    @GET("translate?from=eng&dest=pol&format=json&pretty=true")
    fun translate(@Query("phrase") phrase: String): Observable<Translate>
}