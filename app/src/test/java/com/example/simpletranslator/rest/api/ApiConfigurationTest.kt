package com.example.simpletranslator.rest.api

import com.example.simpletranslator.rest.api.model.Translate
import com.example.simpletranslator.testUtils.loadTestResource
import com.google.gson.Gson
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class ApiConfigurationTest {

    @Test
    fun verifyApiConfiguration(){
        //given
        val server = MockWebServer()
        val requestBody = loadTestResource("response.json")
        server.enqueue(MockResponse().setBody(requestBody))
        val service = ApiConfiguration()
                .createConfiguration(server.url("/"))
                .create(TranslationService::class.java)

        //when
        val translation = service.translate("word").blockingFirst()

        //then
        val request = server.takeRequest()
        assertEquals("/translate?from=eng&dest=pol&format=json&pretty=true&phrase=word", request.path)
        assertEquals("application/json", request.getHeader("Accept"))
        assertEquals(translation, Gson().fromJson(requestBody, Translate::class.java))
        server.shutdown()
    }
}