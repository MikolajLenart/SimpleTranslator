package com.example.simpletranslator.database

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.test.assertEquals

@RunWith(AndroidJUnit4::class)
class TranslationDaoTest {

    lateinit var dao: TranslationDao
    lateinit var database: TranslationDatabase

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getTargetContext()
        database = Room.inMemoryDatabaseBuilder(context, TranslationDatabase::class.java).build()
        dao = database.translationDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun shouldStoreDataAndReturnCorrectValue() {
        //given
        val entity = TranslationEntity("word", "słowo")

        //when
        dao.insertTranslation(entity)

        //then
        val result = dao.getTranslation("word").blockingGet()
        assertEquals("słowo", result)
    }

    @Test
    fun shouldReturnTrueIfValueExists() {
        //given
        val entity = TranslationEntity("word", "słowo")

        //when
        dao.insertTranslation(entity)

        //then
        val expectedTrue = dao.checkIfTranslationExists("word").blockingGet()
        val expectedFalse = dao.checkIfTranslationExists("another").blockingGet()
        assertEquals(true, expectedTrue)
        assertEquals(false, expectedFalse)
    }
}