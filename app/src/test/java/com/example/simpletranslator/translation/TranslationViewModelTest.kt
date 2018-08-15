package com.example.simpletranslator.translation

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModelProviders
import com.example.simpletranslator.MainActivity
import com.example.simpletranslator.repository.TranslationRepository
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner


@RunWith(RobolectricTestRunner::class)
class TranslationViewModelTest {

    @Mock
    lateinit var repository: TranslationRepository

    @Mock
    lateinit var mockedData: MutableLiveData<String>

    @Mock
    lateinit var compositeDisposable: CompositeDisposable

    lateinit var viewModel: TranslationViewModel

    @Before
    fun setUp() {
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        MockitoAnnotations.initMocks(this)
        viewModel = TranslationViewModel(repository, mockedData, compositeDisposable)
    }

    @After
    fun tearDown() {
        RxJavaPlugins.reset()
        RxAndroidPlugins.reset()
    }

    @Test
    fun shouldPostResultOfTranslation() {
        //given
        val sourcedPhrase = "test"
        val resultPhrase = "result"
        whenever(repository.getTranslation(sourcedPhrase)).thenReturn(Single.just(resultPhrase))

        //when
        viewModel.translate(sourcedPhrase)

        //then
        verify(mockedData).postValue(resultPhrase)
    }

    @Test
    fun shouldAddDisposableToComposite() {
        //given
        val sourcedPhrase = "test"
        val resultPhrase = "result"
        whenever(repository.getTranslation(sourcedPhrase)).thenReturn(Single.just(resultPhrase))

        //when
        viewModel.translate(sourcedPhrase)

        //then
        verify(compositeDisposable).add(any())
    }

    @Test
    fun shouldDisposeDisposableOnCleared() {
        //given
        val controller = Robolectric.buildActivity(MainActivity::class.java).create().start().visible();
        val factory = TranslationViewModelFactory(repository, mockedData, compositeDisposable)
        viewModel = ViewModelProviders.of(controller.get(), factory).get(TranslationViewModel::class.java)

        //when
        controller.destroy()

        //then
        verify(compositeDisposable).dispose()
    }
}