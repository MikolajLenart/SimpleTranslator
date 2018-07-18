package com.example.simpletranslator.rest.api.model

import com.google.gson.annotations.SerializedName

data class Translate(@SerializedName("result")
                     val result: String = "",
                     @SerializedName("tuc")
                     val tuc: List<TucItem>?,
                     @SerializedName("phrase")
                     val phrase: String = "",
                     @SerializedName("from")
                     val from: String = "",
                     @SerializedName("dest")
                     val dest: String = "",
                     @SerializedName("authors")
                     val authors: Authors)