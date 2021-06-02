package it.mem.myapplicationmarvel.model.entity

import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("path") val path: String,
    @SerializedName("extension") val extension: String
)
