package it.mem.myapplicationmarvel.data.model.entity

import com.google.gson.annotations.SerializedName

data class Item (
    @SerializedName("resourceURI") val resourceURI: String,
    @SerializedName("name") val name: String,
    @SerializedName("role") val role: String
    )