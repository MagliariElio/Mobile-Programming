package it.mem.myapplicationmarvel.model.entity

import com.google.gson.annotations.SerializedName

data class Stories(
    @SerializedName("available") val available: Int, //21
    @SerializedName("collectionURI") val collectionURI: String, //http://gateway.marvel.com/v1/public/characters/1011334/stories
    @SerializedName("items") val items: List<Item>,
    @SerializedName("returned") val returned: Int //20
)