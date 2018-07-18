package com.example.simpletranslator.rest.api.model

import com.google.gson.annotations.SerializedName

data class Authors(@SerializedName("U")
              val u: String = "",
              @SerializedName("id")
              val id: Int = 0,
              @SerializedName("N")
              val n: String = "",
              @SerializedName("url")
              val url: String = "")