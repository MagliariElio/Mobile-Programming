package it.mem.myapplicationmarvel.data.model.entity

import com.google.gson.annotations.SerializedName

data class Items(
    @SerializedName("resourceURI") val resourceURI: String,
    @SerializedName("name") val name: String,
    @SerializedName("role") val role: String
)
