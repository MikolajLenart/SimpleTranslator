package com.example.simpletranslator

import android.arch.persistence.room.Room
import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.example.simpletranslator.application.DaggerAppComponent
import com.example.simpletranslator.application.SimpleTranslatorApplication
import com.example.simpletranslator.database.TranslationDatabase
import com.example.simpletranslator.testUtils.RxIdlingResourceRule
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class TranslationAcceptanceTest {

    @get:Rule
    var activityRule = ActivityTestRule(MainActivity::class.java, true, false)

    @get:Rule
    val idlingResourceRule = RxIdlingResourceRule()

    val server by lazy {
        MockWebServer()
    }

    @Before
    fun setUp() {
        val dispatcher = object : Dispatcher() {

            override fun dispatch(request: RecordedRequest?): MockResponse {
                return if (request?.path.equals("/translate?from=eng&dest=pol&format=json&pretty=true&phrase=word")) {
                    MockResponse().setBody(loadTestResource("response.json"))
                } else MockResponse().setStatus("404")
            }
        }
        server.setDispatcher(dispatcher)
        val application = InstrumentationRegistry.getTargetContext().applicationContext
        val component = DaggerAppComponent.builder()
                .baseURl(server.url("/"))
                .database(Room.inMemoryDatabaseBuilder(application, TranslationDatabase::class.java).build())
                .create(application as SimpleTranslatorApplication)
        component.inject(application)
        activityRule.launchActivity(Intent())
    }

    fun loadTestResource(resourceName: String): String {
        val inputStream = InstrumentationRegistry.getTargetContext().resources.assets.open(resourceName)
        return inputStream.bufferedReader().use { it.readText() }
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun verifyTranslation() {
        //given
        onView(withId(R.id.translationInput)).perform(ViewActions.typeText("word"), ViewActions.closeSoftKeyboard())

        //when
        onView(withId(R.id.translateButton)).perform(click())

        //then
        onView(withId(R.id.translateResult)).check(ViewAssertions.matches(ViewMatchers.withText("słowo")))
    }
}