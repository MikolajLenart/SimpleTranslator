package com.example.simpletranslator

import android.arch.lifecycle.MutableLiveData
import com.example.simpletranslator.translation.TranslationViewModel
import com.example.simpletranslator.translation.TranslationViewModelFactory
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import javax.inject.Inject
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.test.assertEquals

@RunWith(RobolectricTestRunner::class)
@Config(application = TestApplication::class)
class MainActivityTest{

    @Inject
    lateinit var viewModelFactory: TranslationViewModelFactory

    @Mock
    lateinit var viewModel: TranslationViewModel

    val activity by lazy {
        Robolectric.setupActivity(MainActivity::class.java)
    }

    val liveData = MutableLiveData<String>()

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)
        val application = RuntimeEnvironment.application as TestApplication
        application.testComponent.inject(this)
        whenever(viewModelFactory.create<TranslationViewModel>(any())).thenReturn(viewModel)
        whenever(viewModel.translationResultData).thenReturn(liveData)
    }

    @Test
    fun shouldTranslateEnteredPhrase(){
        //given
        val sourcedPhrase = "test"
        activity.translationInput.setText(sourcedPhrase)

        //when
        activity.translateButton.performClick()

        //then
        verify(viewModel).translate(sourcedPhrase)
    }

    @Test
    fun shouldDisplayReceivedTranslation(){
        //given
        val resultPhrase = "result"

        //when
        liveData.postValue(resultPhrase)

        //then
        assertEquals(resultPhrase, activity.translateResult.text.toString())
    }
}