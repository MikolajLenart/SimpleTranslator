package com.example.simpletranslator

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.runner.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TranslationAcceptanceTest {

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