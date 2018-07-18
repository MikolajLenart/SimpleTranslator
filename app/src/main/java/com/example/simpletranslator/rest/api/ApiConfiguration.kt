package com.example.simpletranslator.rest.api

import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfiguration {

    fun createConfiguration(baseUrl: HttpUrl): Retrofit {

        val client = OkHttpClient.Builder()
                .addInterceptor {
                    it.proceed(it
                            .request()
                            .newBuilder()
                            .addHeader("Accept", "application/json")
                            .build())
                }
                .build()

        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build()
    }
}