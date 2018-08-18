package com.example.simpletranslator.translation

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.simpletranslator.repository.TranslationRepository
import io.reactivex.disposables.CompositeDisposable

class TranslationViewModelFactory(private val repository: TranslationRepository, private val translationLiveData: MutableLiveData<String> = MutableLiveData(),
                                  private val  compositeDisposable: CompositeDisposable = CompositeDisposable()) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TranslationViewModel::class.java)) {
            return TranslationViewModel(repository, translationLiveData, compositeDisposable) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}