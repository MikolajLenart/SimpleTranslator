package com.example.simpletranslator.repository

import com.example.simpletranslator.database.TranslationDao
import com.example.simpletranslator.database.TranslationEntity
import com.example.simpletranslator.rest.api.TranslationService
import com.example.simpletranslator.rest.api.model.Phrase
import com.example.simpletranslator.rest.api.model.Translate
import com.example.simpletranslator.rest.api.model.TucItem
import com.nhaarman.mockito_kotlin.*
import io.reactivex.Observable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

@RunWith(JUnit4::class)
class TranslationRepositoryTest {

    @Mock
    lateinit var service: TranslationService

    @Mock
    lateinit var dao: TranslationDao

    lateinit var repository: TranslationRepository

    private val testPhrase = "test"
    private val result = "result"
    private val dbResult = "dbResult"

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        repository = TranslationRepository(service, dao)
    }

    @Test
    fun shouldFetchDataFromInternet() {
        //given
        mockTranslationResponse()

        //when
        repository.getTranslation(testPhrase).blockingGet()

        //then
        verify(service).translate(testPhrase)
    }

    @Test
    fun shouldStoreResultInDatabase() {
        //given
        mockTranslationResponse()

        //when
        repository.getTranslation(testPhrase).blockingGet()

        //then
        val captor = argumentCaptor<TranslationEntity>()
        verify(dao).insertTranslation(captor.capture())
        val entity = captor.firstValue
        assertEquals(entity.sourcedPhrase, testPhrase)
        assertEquals(entity.translatedPhrase, result)
    }

    @Test
    fun shouldThrowExceptionWhenTucIsEmpty() {
        //given
        val mockedTranslation = mock<Translate> {}
        given(mockedTranslation.tuc).willReturn(emptyList())
        whenever(service.translate(testPhrase)).thenReturn(Observable.just(mockedTranslation))
        whenever(dao.checkIfTranslationExists(testPhrase)).thenReturn(Single.just(false))

        //when
        val errorObservable = repository.getTranslation(testPhrase)

        //then
        assertFailsWith(NullPointerException::class) {
            errorObservable.blockingGet()
        }
    }

    @Test
    fun shouldReturnResultFromDatabase() {
        //given
        mockTranslationResponse()

        //when
        val expected = repository.getTranslation(testPhrase).blockingGet()

        //then
        assertEquals(dbResult, expected)
    }

    @Test
    fun shouldReturnPreviouslyFetchedResult() {
        //given
        whenever(dao.checkIfTranslationExists(testPhrase)).thenReturn(Single.just(true))
        whenever(dao.getTranslation(testPhrase)).thenReturn(Single.just(result))

        //when
        val expected = repository.getTranslation(testPhrase).blockingGet()

        //then
        verifyZeroInteractions(service)
        assertEquals(result, expected)
    }

    private fun mockTranslationResponse() {
        val mockedTranslation = createMockedTranslation(result)
        whenever(service.translate(testPhrase)).thenReturn(Observable.just(mockedTranslation))
        whenever(dao.getTranslation(testPhrase)).thenReturn(Single.just(dbResult))
        whenever(dao.checkIfTranslationExists(testPhrase)).thenReturn(Single.just(false))
    }

    private fun createMockedTranslation(result: String): Translate {
        val mockedTranslation = mock<Translate> {}
        val mockedtItem = mock<TucItem> { }
        val mockedPhrase = mock<Phrase> { }
        whenever(mockedPhrase.text).thenReturn(result)
        whenever(mockedtItem.phrase).thenReturn(mockedPhrase)
        whenever(mockedTranslation.tuc).thenReturn(listOf(mockedtItem))
        return mockedTranslation
    }
}