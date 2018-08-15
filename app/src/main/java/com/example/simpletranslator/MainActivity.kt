package com.example.simpletranslator

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.example.simpletranslator.translation.TranslationViewModel
import com.example.simpletranslator.translation.TranslationViewModelFactory
import dagger.android.DaggerActivity
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: TranslationViewModelFactory

    val viewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(TranslationViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        translateButton.setOnClickListener {
            viewModel.translate(translationInput.text.toString())
        }
        viewModel.translationResultData.observe(this, Observer {
            translateResult.text = it
        })
    }
}
