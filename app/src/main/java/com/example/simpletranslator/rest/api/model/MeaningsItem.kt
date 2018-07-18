package com.example.simpletranslator.rest.api.model

import com.google.gson.annotations.SerializedName

data class MeaningsItem(@SerializedName("language")
                        val language: String = "",
                        @SerializedName("text")
                        val text: String = "")