package it.mem.myapplicationmarvel.model.entity

import com.google.gson.annotations.SerializedName

data class Price (
    @SerializedName("type") val type: String,
    @SerializedName("price") val price: Double
        )
