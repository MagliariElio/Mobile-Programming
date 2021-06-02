package it.mem.myapplicationmarvel.model.entity

import com.google.gson.annotations.SerializedName

data class Comics (
    @SerializedName("available") val available: Int, //12
    @SerializedName("collectionURI") val collectionURI: String, //http://gateway.marvel.com/v1/public/characters/1011334/comics
    @SerializedName("items") val items: List<Item>,
    @SerializedName("returned") val returned: Int //12
)
