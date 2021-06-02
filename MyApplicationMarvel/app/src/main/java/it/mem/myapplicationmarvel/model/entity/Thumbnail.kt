package it.mem.myapplicationmarvel.model.entity

import com.google.gson.annotations.SerializedName

data class Thumbnail(
    @SerializedName("path") val path: String, //http://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784
    @SerializedName("extension") val extension: String //jpg
)
