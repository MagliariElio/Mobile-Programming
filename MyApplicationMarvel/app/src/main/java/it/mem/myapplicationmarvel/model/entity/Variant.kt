package it.mem.myapplicationmarvel.model.entity

import com.google.gson.annotations.SerializedName

data class Variant(
    @SerializedName("resourceURI") val resourceURI: String,
    @SerializedName("name") val name: String
)
