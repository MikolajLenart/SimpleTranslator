package com.example.simpletranslator.rest.api.model

import com.google.gson.annotations.SerializedName

data class TucItem(@SerializedName("phrase")
                   val phrase: Phrase,
                   @SerializedName("meanings")
                   val meanings: List<MeaningsItem>?,
                   @SerializedName("meaningId")
                   val meaningId: Long = 0,
                   @SerializedName("authors")
                   val authors: List<Integer>?)