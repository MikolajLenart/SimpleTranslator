package com.example.simpletranslator.rest.api.model

import com.google.gson.annotations.SerializedName

data class Phrase(@SerializedName("language")
                  val language: String = "",
                  @SerializedName("text")
                  val text: String = "")