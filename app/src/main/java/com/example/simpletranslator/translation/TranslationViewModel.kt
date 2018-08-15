package com.example.simpletranslator.translation

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.example.simpletranslator.repository.TranslationRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class TranslationViewModel(private val repository: TranslationRepository, private val translationLiveData: MutableLiveData<String> = MutableLiveData<String>(),
                           private val  compositeDisposable: CompositeDisposable = CompositeDisposable()): ViewModel() {

    val translationResultData: LiveData<String>
        get() = translationLiveData

    fun translate(phrase: String) {
        compositeDisposable.add(repository.getTranslation(phrase)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(translationLiveData::postValue))
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}